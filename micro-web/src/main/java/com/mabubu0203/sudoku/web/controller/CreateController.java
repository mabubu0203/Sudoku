package com.mabubu0203.sudoku.web.controller;

import com.mabubu0203.sudoku.constants.WebUrlConstants;
import com.mabubu0203.sudoku.web.form.CreateForm;
import com.mabubu0203.sudoku.web.form.validator.CreateFormValidator;
import com.mabubu0203.sudoku.web.helper.CreateHelper;
import com.mabubu0203.sudoku.web.helper.bean.HelperBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/"})
public class CreateController {

    private final RestTemplateBuilder restTemplateBuilder;
    private final CreateHelper createHelper;

    /**
     * <br>
     *
     * @param binder
     * @author uratamanabu
     * @since 1.0
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {

        Optional<Object> object = Optional.ofNullable(binder.getTarget());
        object
                .filter((notNullBinder) -> notNullBinder instanceof CreateForm)
                .ifPresent(o -> binder.addValidators(new CreateFormValidator()));
    }

    /**
     * /createAnswerの初期ページへ遷移します。<br>
     *
     * @param form
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_CREATE_ANSWER})
    public String okCreateAnswer(final CreateForm form, final Model model) {

        HelperBean handleBean = new HelperBean().setModel(model);
        this.createHelper.createAnswer(handleBean);
        return WebUrlConstants.Forward.CREATE_ANSWER.getPath();
    }

    /**
     * ANSWER_INFO_TBLとSCORE_INFO_TBLにレコードを追加し、作成完了ページへ遷移します。<br>
     * 一意制約等が発生しレコードが追加できなかった時は、エラーメッセージを作成完了ページへ表示します。<br>
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_COMPLETE_ANSWER})
    public String okCompleteAnswer(
            @Validated @ModelAttribute final CreateForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return okCreateAnswer(form, model);
        } else {
            HelperBean handleBean = new HelperBean().setForm(form).setModel(model);
            HttpStatus result = this.createHelper.completeAnswer(restTemplateBuilder.build(), handleBean);
            switch (result) {
                case OK:
                    return WebUrlConstants.Forward.COMPLETE_ANSWER.getPath();
                case CONFLICT:
                default:
                    return okCreateAnswer(form, model);
            }
        }
    }

}
