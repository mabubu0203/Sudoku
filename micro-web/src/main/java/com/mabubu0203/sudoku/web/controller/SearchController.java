package com.mabubu0203.sudoku.web.controller;

import com.mabubu0203.sudoku.constants.WebUrlConstants;
import com.mabubu0203.sudoku.validator.constraint.KeyHash;
import com.mabubu0203.sudoku.validator.constraint.Type;
import com.mabubu0203.sudoku.web.form.DetailForm;
import com.mabubu0203.sudoku.web.form.SearchForm;
import com.mabubu0203.sudoku.web.form.validator.DetailFormValidator;
import com.mabubu0203.sudoku.web.form.validator.SearchFromValidator;
import com.mabubu0203.sudoku.web.helper.SearchHelper;
import com.mabubu0203.sudoku.web.helper.bean.HelperBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping(value = {"/"})
public class SearchController {

    private final RestOperations restOperations;
    private final SearchHelper searchHelper;

    public SearchController(
            final RestTemplateBuilder restTemplateBuilder,
            final SearchHelper searchHelper) {
        this.restOperations = restTemplateBuilder.build(); // Builderのbuildメソッドを呼び出しRestTemplateを生成
        this.searchHelper = searchHelper;
    }

    /**
     * @param binder
     * @author uratamanabu
     * @since 1.0
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {

        Optional<Object> object = Optional.ofNullable(binder.getTarget());
        object
                .filter((notNullBinder) -> notNullBinder instanceof DetailForm)
                .ifPresent(o -> binder.addValidators(new DetailFormValidator()));
        object
                .filter((notNullBinder) -> notNullBinder instanceof SearchForm)
                .ifPresent(o -> binder.addValidators(new SearchFromValidator()));
    }

    /**
     * /searchAnswerの初期ページへ遷移します。
     *
     * @param form
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_SEARCH_ANSWER})
    public String searchAnswer(@ModelAttribute final SearchForm form, final Model model) {

        HelperBean handleBean = new HelperBean().setModel(model);
        this.searchHelper.searchAnswer(handleBean);
        return WebUrlConstants.Forward.SEARCH_ANSWER.getPath();
    }

    /**
     * /isSearchの初期ページへ遷移します。
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {"isSearch"})
    public String isSearch(
            @Validated @ModelAttribute final SearchForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
        } else {
            HelperBean handleBean = new HelperBean().setForm(form).setModel(model);
            this.searchHelper.isSearch(restOperations, handleBean);
        }
        return searchAnswer(form, model);
    }

    /**
     * /detailの初期ページへ遷移します。
     *
     * @param type
     * @param keyHash
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(
            value = {WebUrlConstants.URL_DETAIL},
            params = {"type", "keyHash"}
    )
    public String detail(
            @RequestParam("type") @Type(message = "選択肢から選択してください。") final int type,
            @RequestParam("keyHash") @KeyHash(message = "数値64桁を入力しましょう。") final String keyHash,
            final Model model) {

        if (keyHash.length() == 64) {
            DetailForm form = new DetailForm().setType(type).setKeyHash(keyHash);
            HelperBean handleBean = new HelperBean().setForm(form).setModel(model);
            this.searchHelper.detail(handleBean);
            return WebUrlConstants.Forward.DETAIL.getPath();
        } else {
            return "error";
        }
    }

    /**
     * /playNumberPlaceDetailの初期ページへ遷移します。
     *
     * @param form
     * @param bindingResult
     * @param model
     * @return String
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {"playNumberPlaceDetail"})
    public String playNumberPlaceDetail(
            @Validated @ModelAttribute final DetailForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        } else {
            HelperBean handleBean = new HelperBean().setForm(form).setModel(model);
            this.searchHelper.playNumberPlaceDetail(restOperations, handleBean);
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        }
    }

}
