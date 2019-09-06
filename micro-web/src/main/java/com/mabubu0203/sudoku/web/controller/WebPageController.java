package com.mabubu0203.sudoku.web.controller;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.WebUrlConstants;
import com.mabubu0203.sudoku.controller.WebBaseController;
import com.mabubu0203.sudoku.web.form.ScoreForm;
import com.mabubu0203.sudoku.web.form.validator.ScoreFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class WebPageController extends WebBaseController {

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
                .filter((notNullBinder) -> notNullBinder instanceof ScoreForm)
                .ifPresent(o -> binder.addValidators(new ScoreFormValidator()));
    }

    /**
     * <br>
     *
     * @return Topページ
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_TOP})
    public String okTop() {
        return WebUrlConstants.Forward.TOP.getPath();
    }

    /**
     * <br>
     *
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_CHOICE_QUESTION})
    public String okChoice() {
        return WebUrlConstants.Forward.CHOICE_QUESTION.getPath();
    }

    /**
     * /completeNumberPlaceの初期ページへ遷移します。<br>
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_BEST_SCORE_COMPLETE})
    public String bestScoreComplete(
            @Validated @ModelAttribute final ScoreForm form,
            final BindingResult bindingResult,
            final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return CommonConstants.EMPTY_STR;
        } else {
            return WebUrlConstants.Forward.BEST_SCORE_COMPLETE.getPath();
        }
    }

    /**
     * /completeNumberPlaceの初期ページへ遷移します。<br>
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_COMPLETE_NUMBER_PLACE})
    public String completeNumberPlace(
            @Validated @ModelAttribute final ScoreForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return CommonConstants.EMPTY_STR;
        } else {
            return WebUrlConstants.Forward.COMPLETE_NUMBER_PLACE.getPath();
        }
    }

    /**
     * /introduceの初期ページへ遷移します。<br>
     *
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_INTRODUCE})
    public String introduce() {
        return WebUrlConstants.Forward.INTRODUCE.getPath();
    }

    /**
     * /linkListの初期ページへ遷移します。<br>
     *
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_LINK_LIST})
    public String linkList() {
        return WebUrlConstants.Forward.LINK_LIST.getPath();
    }

}
