package com.mabubu0203.sudoku.web.controller;

import com.mabubu0203.sudoku.constants.WebUrlConstants;
import com.mabubu0203.sudoku.web.form.CreateForm;
import com.mabubu0203.sudoku.web.form.PlayForm;
import com.mabubu0203.sudoku.web.form.ScoreForm;
import com.mabubu0203.sudoku.web.form.validator.CreateFormValidator;
import com.mabubu0203.sudoku.web.form.validator.PlayFormValidator;
import com.mabubu0203.sudoku.web.helper.PlayHelper;
import com.mabubu0203.sudoku.web.helper.bean.HelperBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
public class PlayController {

    private final RestTemplateBuilder restTemplateBuilder;
    private final PlayHelper playHelper;

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
        object
                .filter((notNullBinder) -> notNullBinder instanceof PlayForm)
                .ifPresent(o -> binder.addValidators(new PlayFormValidator()));
    }

    /**
     * /createQuestionの初期ページへ遷移します。<br>
     *
     * @param form
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_CREATE_QUESTION})
    public String okCreateQuestion(final CreateForm form, final Model model) {

        HelperBean handleBean = new HelperBean().setModel(model);
        this.playHelper.createQuestion(handleBean);
        return WebUrlConstants.Forward.CREATE_QUESTION.getPath();
    }

    /**
     * /playNumberPlaceの初期ページへ遷移します。<br>
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_PLAY_NUMBER_PLACE})
    public String okPlayNumberPlace(
            @Validated @ModelAttribute final CreateForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return okCreateQuestion(form, model);
        } else {
            HelperBean handleBean = new HelperBean().setForm(form).setModel(model);
            this.playHelper.playNumberPlace(restTemplateBuilder.build(), handleBean);
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        }
    }

    /**
     * /isCheckの初期ページへ遷移します。<br>
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {"isCheck"})
    public String okIsCheck(
            @Validated @ModelAttribute final PlayForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        } else {
            HelperBean handleBean = new HelperBean().setForm(form).setModel(model);
            int result = this.playHelper.isCheck(restTemplateBuilder.build(), handleBean);
            switch (result) {
                case 1:
                    return WebUrlConstants.Forward.BEST_SCORE.getPath();
                case 2:
                    return WebUrlConstants.Forward.COMPLETE_NUMBER_PLACE.getPath();
                case 3:
                    return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
                default:
                    return "";
            }
        }
    }

    /**
     * /bestScoreの初期ページへ遷移します。<br>
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_BEST_SCORE})
    public String bestScore(
            @Validated @ModelAttribute final ScoreForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return WebUrlConstants.Forward.BEST_SCORE.getPath();
        } else {
            HelperBean handleBean = new HelperBean().setForm(form).setModel(model);
            this.playHelper.bestScore(restTemplateBuilder.build(), handleBean);
            return WebUrlConstants.Forward.BEST_SCORE_COMPLETE.getPath();
        }
    }

}
