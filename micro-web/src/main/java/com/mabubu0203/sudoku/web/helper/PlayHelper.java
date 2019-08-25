package com.mabubu0203.sudoku.web.helper;

import com.mabubu0203.sudoku.clients.api.RestApiSearchEndPoints;
import com.mabubu0203.sudoku.clients.api.RestApiUpdateEndPoints;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.RecordBean;
import com.mabubu0203.sudoku.interfaces.request.UpdateSudokuScoreRequestBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.logic.SudokuModule;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.ESMapWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import com.mabubu0203.sudoku.web.form.CreateForm;
import com.mabubu0203.sudoku.web.form.PlayForm;
import com.mabubu0203.sudoku.web.form.ScoreForm;
import com.mabubu0203.sudoku.web.helper.bean.HelperBean;
import com.mabubu0203.sudoku.web.utils.CompareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PlayHelper {

    private final SudokuModule sudokuModule;
    private final RestApiSearchEndPoints restApiSearchEndPoints;
    private final RestApiUpdateEndPoints restApiUpdateEndPoints;

    private final ModelMapper modelMapper;

    @Value("${sudoku.uri.api}")
    private String sudokuUriApi;

    /**
     * <br>
     *
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void createQuestion(final HelperBean bean) {

        Model model = bean.getModel();
        if (Objects.isNull(model)) {
            throw new SudokuApplicationException();
        } else {
            model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
            model.addAttribute("selectLevels", ESMapWrapUtils.getSelectLevels());
        }
    }

    /**
     * <br>
     *
     * @param restOperations
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void playNumberPlace(final RestOperations restOperations, final HelperBean bean) {

        CreateForm form = (CreateForm) bean.getForm();

        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }

        try {
            NumberPlaceBean numberPlaceBean = restApiSearchEndPoints
                    .sudoku(restOperations, form.getSelectType(), CommonConstants.EMPTY_STR);
            // 一件から虫食いのリストを取得します。
            numberPlaceBean =
                    sudokuModule.filteredOfLevel(form.getSelectType(), numberPlaceBean, form.getSelectLevel());
            PlayForm playForm = modelMapper.map(numberPlaceBean, PlayForm.class);
            playForm.setCount(0);
            playForm.setScore(SudokuUtils.calculationScore(form.getSelectType(), form.getSelectLevel()));
            model.addAttribute("playForm", playForm);
            model.addAttribute("selectNums", ESListWrapUtils.getSelectNum(form.getSelectType()));
            model.addAttribute("selectCells", ESListWrapUtils.createCells(form.getSelectType()));
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }
    }

    /**
     * <br>
     *
     * @param restOperations
     * @param bean
     * @return int
     * @author uratamanabu
     * @since 1.0
     */
    public int isCheck(final RestOperations restOperations, final HelperBean bean) {

        PlayForm form = (PlayForm) bean.getForm();
        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }

        try {
            NumberPlaceBean numberPlaceBean = restApiSearchEndPoints
                    .sudoku(restOperations, form.getType(), form.getKeyHash());
            CompareUtil.playFormCompareAnswer(form, numberPlaceBean);
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }

        if (form.isCompareFlg()) {
            try {
                ScoreResponseBean score = restApiSearchEndPoints
                        .score(restOperations, form.getType(), form.getKeyHash());
                ScoreForm scoreForm = modelMapper.map(form, ScoreForm.class);
                if (scoreForm.getScore() > score.getScore()) {
                    scoreForm.setName(CommonConstants.EMPTY_STR);
                    scoreForm.setMemo(CommonConstants.EMPTY_STR);
                    // modelに詰め込みます。
                    model.addAttribute("scoreForm", scoreForm);
                    model.addAttribute("hiScore", score.getScore());
                    // score によって、ベストスコアに進む
                    return 1;
                } else {
                    scoreForm.setName(score.getName());
                    scoreForm.setMemo(score.getMemo());
                    // modelに詰め込みます。
                    model.addAttribute("scoreForm", scoreForm);
                    model.addAttribute("hiScore", score.getScore());
                    // score によって、完了に進む
                    return 2;
                }
            } catch (SudokuApplicationException e) {
                e.printStackTrace();
                throw new SudokuApplicationException();
            }
        } else {
            // modelに詰め込みます。
            model.addAttribute("form", form);
            model.addAttribute("selectNums", ESListWrapUtils.getSelectNum(form.getType()));
            model.addAttribute("selectCells", ESListWrapUtils.createCells(form.getType()));
            return 3;
        }
    }

    /**
     * <br>
     *
     * @param restOperations
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public void bestScore(final RestOperations restOperations, final HelperBean bean) {

        ScoreForm form = (ScoreForm) bean.getForm();
        Model model = bean.getModel();
        if (Objects.isNull(form) || Objects.isNull(model)) {
            throw new SudokuApplicationException();
        }

        Optional<Long> noOpt = Optional.ofNullable(updateScore(restOperations, form));
        if (noOpt.isPresent()) {
            // modelに詰め込みます。
            model.addAttribute("scoreForm", form);
            model.addAttribute("message", "登録完了です。");
            model.addAttribute("no", noOpt.get());
            List<RecordBean> list = new ArrayList<>();
            RecordBean rBean = new RecordBean();
            rBean.setNo(noOpt.get().longValue());
            rBean.setType(form.getType());
            rBean.setKeyHash(form.getKeyHash());
            rBean.setScore(form.getScore());
            rBean.setName(form.getName());
            rBean.setMemo(form.getMemo());
            list.add(rBean);
            model.addAttribute("list", list);
        } else {
            model.addAttribute("message", "更新失敗です。");
            log.debug("insertAnswerAndScore()でロールバックしています。");
        }
    }

    private Long updateScore(final RestOperations restOperations, ScoreForm form) {

        UpdateSudokuScoreRequestBean request =
                new ModelMapper().map(form, UpdateSudokuScoreRequestBean.class);

        Optional<Long> noOpt = restApiUpdateEndPoints.update(restOperations, request);
        if (noOpt.isPresent()) {
            return noOpt.get();
        } else {
            return null;
        }
    }

}
