package com.mabubu0203.sudoku.web.controller;


import com.mabubu0203.sudoku.constants.WebUrlConstants;
import com.mabubu0203.sudoku.controller.WebBaseController;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import com.mabubu0203.sudoku.validator.constraint.KeyHash;
import com.mabubu0203.sudoku.validator.constraint.Type;
import com.mabubu0203.sudoku.web.deprecated.LogicHandleBean;
import com.mabubu0203.sudoku.web.form.CreateForm;
import com.mabubu0203.sudoku.web.form.DetailForm;
import com.mabubu0203.sudoku.web.form.PlayForm;
import com.mabubu0203.sudoku.web.form.ScoreForm;
import com.mabubu0203.sudoku.web.form.SearchForm;
import com.mabubu0203.sudoku.web.form.validator.CreateFormValidator;
import com.mabubu0203.sudoku.web.form.validator.DetailFormValidator;
import com.mabubu0203.sudoku.web.form.validator.PlayFormValidator;
import com.mabubu0203.sudoku.web.form.validator.ScoreFormValidator;
import com.mabubu0203.sudoku.web.form.validator.SearchFromValidator;
import com.mabubu0203.sudoku.web.logic.CreateLogic;
import com.mabubu0203.sudoku.web.logic.PlayLogic;
import com.mabubu0203.sudoku.web.logic.SearchLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
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
public class WebPageController extends WebBaseController {

    private final RestOperations restOperations;
    /**
     * AnswerInfoServiceを配備します。
     */
    private final AnswerInfoService answerInfoService;
    /**
     * ScoreInfoServiceを配備します。
     */
    private final ScoreInfoService scoreInfoService;

    public WebPageController(
            RestTemplateBuilder restTemplateBuilder,
            AnswerInfoService answerInfoService,
            ScoreInfoService scoreInfoService) {
        // コンストラクタインジェクション！！
        this.restOperations = restTemplateBuilder.build(); // Builderのbuildメソッドを呼び出しRestTemplateを生成
        this.answerInfoService = answerInfoService;
        this.scoreInfoService = scoreInfoService;
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {

        Optional<Object> object = Optional.ofNullable(binder.getTarget());
        object
                .filter((notNullBinder) -> CreateForm.class.equals(notNullBinder.getClass()))
                .ifPresent(o -> binder.addValidators(new CreateFormValidator()));
        object
                .filter((notNullBinder) -> DetailForm.class.equals(notNullBinder.getClass()))
                .ifPresent(o -> binder.addValidators(new DetailFormValidator()));
        object
                .filter((notNullBinder) -> PlayForm.class.equals(notNullBinder.getClass()))
                .ifPresent(o -> binder.addValidators(new PlayFormValidator()));
        object
                .filter((notNullBinder) -> ScoreForm.class.equals(notNullBinder.getClass()))
                .ifPresent(o -> binder.addValidators(new ScoreFormValidator()));
        object
                .filter((notNullBinder) -> SearchForm.class.equals(notNullBinder.getClass()))
                .ifPresent(o -> binder.addValidators(new SearchFromValidator()));
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_TOP})
    public String okTop() {
        return WebUrlConstants.Forward.TOP.getPath();
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_TOP})
    public String notTop() {
        return "error";
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_CHOICE_QUESTION})
    public String okChoice() {
        return WebUrlConstants.Forward.CHOICE_QUESTION.getPath();
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_CHOICE_QUESTION})
    public String notChoice() {
        return "error";
    }

    /**
     * /createAnswerの初期ページへ遷移します。
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_CREATE_ANSWER})
    public String okCreateAnswer(final CreateForm form, final Model model) {

        LogicHandleBean handleBean = new LogicHandleBean().setModel(model);
        new CreateLogic().createAnswer(handleBean);
        return WebUrlConstants.Forward.CREATE_ANSWER.getPath();
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_CREATE_ANSWER})
    public String notCreateAnswer() {
        return "error";
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_COMPLETE_ANSWER})
    public String notCompleteAnswer() {
        return "error";
    }

    /**
     * ANSWER_INFO_TBLとSCORE_INFO_TBLにレコードを追加し、作成完了ページへ遷移します。
     * 一意制約等が発生しレコードが追加できなかった時は、エラーメッセージを作成完了ページへ表示します。
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_COMPLETE_ANSWER})
    public String okCompleteAnswer(
            @Validated @ModelAttribute final CreateForm form,
            final BindingResult bindingResult,
            final Model model)
            throws SudokuApplicationException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return okCreateAnswer(form, model);
        } else {
            LogicHandleBean handleBean =
                    new LogicHandleBean()
                            .setForm(form)
                            .setModel(model);
            // TODO:移植中
//                            .setService(Tables.ANSWER_INFO_TBL, answerInfoService)
//                            .setService(Tables.SCORE_INFO_TBL, scoreInfoService);
            HttpStatus result = new CreateLogic().completeAnswer(restOperations, handleBean);
            if (result == HttpStatus.OK) {
                return WebUrlConstants.Forward.COMPLETE_ANSWER.getPath();
            } else if (result == HttpStatus.CONFLICT) {
                return okCreateAnswer(form, model);
            } else {
                return okCreateAnswer(form, model);
            }
        }
    }

    /**
     * /createQuestionの初期ページへ遷移します。
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_CREATE_QUESTION})
    public String okCreateQuestion(final CreateForm form, final Model model) {

        LogicHandleBean handleBean = new LogicHandleBean().setModel(model);
        new PlayLogic().createQuestion(handleBean);
        return WebUrlConstants.Forward.CREATE_QUESTION.getPath();
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @PostMapping(value = {WebUrlConstants.URL_CREATE_QUESTION})
    public String notCreateQuestion() {
        return "error";
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @GetMapping(value = {WebUrlConstants.URL_PLAY_NUMBER_PLACE})
    public String notPlayNumberPlace() {
        return "error";
    }

    /**
     * /playNumberPlaceの初期ページへ遷移します。
     */
    @PostMapping(value = {WebUrlConstants.URL_PLAY_NUMBER_PLACE})
    public String okPlayNumberPlace(
            @Validated @ModelAttribute final CreateForm form,
            final BindingResult bindingResult,
            final Model model)
            throws SudokuApplicationException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return okCreateQuestion(form, model);
        } else {
            LogicHandleBean handleBean =
                    new LogicHandleBean()
                            .setForm(form)
                            .setModel(model);
            // TODO:移植中
//                            .setService(Tables.ANSWER_INFO_TBL, answerInfoService);
            new PlayLogic().playNumberPlace(restOperations, handleBean);
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        }
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @GetMapping(value = {"isCheck"})
    public String notIsCheck() {
        return "error";
    }

    /**
     * /isCheckの初期ページへ遷移します。
     */
    @PostMapping(value = {"isCheck"})
    public String okIsCheck(
            @Validated @ModelAttribute final PlayForm form,
            final BindingResult bindingResult,
            final Model model)
            throws SudokuApplicationException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        } else {
            LogicHandleBean handleBean = new LogicHandleBean().setForm(form).setModel(model);
            log.info(form.toString());
            switch (new PlayLogic().isCheck(restOperations, handleBean)) {
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
     * /bestScoreの初期ページへ遷移します。
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
            LogicHandleBean handleBean =
                    new LogicHandleBean()
                            .setForm(form)
                            .setModel(model);
            // TODO:移植中
//                            .setService(Tables.SCORE_INFO_TBL, scoreInfoService);
            new PlayLogic().bestScore(restOperations, handleBean);
            return WebUrlConstants.Forward.BEST_SCORE_COMPLETE.getPath();
        }
    }

    /**
     * /completeNumberPlaceの初期ページへ遷移します。
     */
    @PostMapping(value = {WebUrlConstants.URL_BEST_SCORE_COMPLETE})
    public String bestScoreComplete(
            @Validated @ModelAttribute final ScoreForm form,
            final BindingResult bindingResult,
            final Model model) {

        return WebUrlConstants.Forward.BEST_SCORE_COMPLETE.getPath();
    }

    /**
     * /completeNumberPlaceの初期ページへ遷移します。
     */
    @PostMapping(value = {WebUrlConstants.URL_COMPLETE_NUMBER_PLACE})
    public String completeNumberPlace(
            @Validated @ModelAttribute final ScoreForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return WebUrlConstants.Forward.COMPLETE_NUMBER_PLACE.getPath();
        } else {
            return WebUrlConstants.Forward.COMPLETE_NUMBER_PLACE.getPath();
        }
    }

    /**
     * /searchAnswerの初期ページへ遷移します。
     */
    @GetMapping(value = {WebUrlConstants.URL_SEARCH_ANSWER})
    public String searchAnswer(@ModelAttribute final SearchForm form, final Model model) {

        LogicHandleBean handleBean = new LogicHandleBean().setModel(model);
        new SearchLogic().searchAnswer(handleBean);
        return WebUrlConstants.Forward.SEARCH_ANSWER.getPath();
    }

    /**
     * /isSearchの初期ページへ遷移します。
     */
    @PostMapping(value = {"isSearch"})
    public String isSearch(
            @Validated @ModelAttribute final SearchForm form,
            final BindingResult bindingResult,
            final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
        } else {
            LogicHandleBean handleBean =
                    new LogicHandleBean()
                            .setForm(form)
                            .setModel(model);
            // TODO:移植中
//                            .setService(Tables.ANSWER_INFO_TBL, answerInfoService);
            new SearchLogic().isSearch(restOperations, handleBean);
        }
        return searchAnswer(form, model);
    }

    /**
     * /detailの初期ページへ遷移します。
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
            DetailForm form = new DetailForm();
            form.setType(type);
            form.setKeyHash(keyHash);
            LogicHandleBean handleBean = new LogicHandleBean().setForm(form).setModel(model);
            new SearchLogic().detail(handleBean);
            return WebUrlConstants.Forward.DETAIL.getPath();
        } else {
            return "error";
        }
    }

    /**
     * /playNumberPlaceDetailの初期ページへ遷移します。
     */
    @PostMapping(value = {"playNumberPlaceDetail"})
    public String playNumberPlaceDetail(
            @Validated @ModelAttribute final DetailForm form,
            final BindingResult bindingResult,
            final Model model)
            throws SudokuApplicationException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationError", "不正な値が入力されました。");
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        } else {
            LogicHandleBean handleBean =
                    new LogicHandleBean()
                            .setForm(form)
                            .setModel(model);
            // TODO:移植中
//                            .setService(Tables.ANSWER_INFO_TBL, answerInfoService);
            new SearchLogic().playNumberPlaceDetail(restOperations, handleBean);
            return WebUrlConstants.Forward.PLAY_NUMBER_PLACE.getPath();
        }
    }

    /**
     * /introduceの初期ページへ遷移します。
     */
    @GetMapping(value = {WebUrlConstants.URL_INTRODUCE})
    public String introduce() {
        return WebUrlConstants.Forward.INTRODUCE.getPath();
    }

    /**
     * /linkListの初期ページへ遷移します。
     */
    @GetMapping(value = {WebUrlConstants.URL_LINK_LIST})
    public String linkList() {
        return WebUrlConstants.Forward.LINK_LIST.getPath();
    }

}
