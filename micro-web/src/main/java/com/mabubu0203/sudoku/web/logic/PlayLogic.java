package com.mabubu0203.sudoku.web.logic;

import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.RecordBean;
import com.mabubu0203.sudoku.interfaces.request.UpdateSudokuScoreRequestBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.logic.deprecated.Sudoku;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.ESMapWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import com.mabubu0203.sudoku.web.deprecated.LogicHandleBean;
import com.mabubu0203.sudoku.web.form.CreateForm;
import com.mabubu0203.sudoku.web.form.PlayForm;
import com.mabubu0203.sudoku.web.form.ScoreForm;
import com.mabubu0203.sudoku.web.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.modelmapper.ModelMapper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class PlayLogic {

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public void createQuestion(final LogicHandleBean handleBean) throws SudokuApplicationException {

        Model model = handleBean.getModel();
        if (model == null) {
            throw new SudokuApplicationException();
        } else {
            model.addAttribute("selectTypes", ESMapWrapUtils.getSelectTypes());
            model.addAttribute("selectLevels", ESMapWrapUtils.getSelectLevels());
        }
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public void playNumberPlace(final RestOperations restOperations, final LogicHandleBean handleBean)
            throws SudokuApplicationException {

        CreateForm form = (CreateForm) handleBean.getForm();
        if (form == null) {
            throw new SudokuApplicationException();
        }
        Model model = handleBean.getModel();
        if (model == null) {
            throw new SudokuApplicationException();
        }
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(form.getSelectType()));
        uriVariables.put("keyHash", "");
        URI uri =
                new UriTemplate(
                        "http://localhost:8085/SudokuApi/searchMaster/sudoku?type={type}&keyHash={keyHash}")
                        .expand(uriVariables);
        try {
            RequestEntity requestEntity = RequestEntity.get(uri).build();
            ResponseEntity<NumberPlaceBean> generateEntity =
                    restOperations.exchange(requestEntity, NumberPlaceBean.class);
            NumberPlaceBean numberPlaceBean = generateEntity.getBody();
            // 一件から虫食いのリストを取得します。
            numberPlaceBean =
                    new Sudoku(form.getSelectType()).filteredOfLevel(numberPlaceBean, form.getSelectLevel());
            PlayForm playForm = new ModelMapper().map(numberPlaceBean, PlayForm.class);
            playForm.setCount(0);
            playForm.setScore(SudokuUtils.calculationScore(form.getSelectType(), form.getSelectLevel()));
            model.addAttribute("playForm", playForm);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }
        model.addAttribute("selectNums", ESListWrapUtils.getSelectNum(form.getSelectType()));
        model.addAttribute("selectCells", ESListWrapUtils.createCells(form.getSelectType()));
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public int isCheck(final RestOperations restOperations, final LogicHandleBean handleBean)
            throws SudokuApplicationException {

        PlayForm form = (PlayForm) handleBean.getForm();
        Model model = handleBean.getModel();
        if (form == null || model == null) {
            throw new SudokuApplicationException();
        }
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(form.getType()));
        uriVariables.put("keyHash", form.getKeyHash());
        URI uri =
                new UriTemplate(
                        "http://localhost:8085/SudokuApi/searchMaster/sudoku?type={type}&keyHash={keyHash}")
                        .expand(uriVariables);
        RequestEntity requestEntity = RequestEntity.get(uri).build();
        try {
            ResponseEntity<NumberPlaceBean> generateEntity =
                    restOperations.exchange(requestEntity, NumberPlaceBean.class);
            NumberPlaceBean numberPlaceBean = generateEntity.getBody();
            CompareUtil.playFormCompareAnswer(form, numberPlaceBean);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }
        if (form.isCompareFlg()) {
            uri =
                    new UriTemplate(
                            "http://localhost:8085/SudokuApi/searchMaster/score?type={type}&keyHash={keyHash}")
                            .expand(uriVariables);
            requestEntity = RequestEntity.get(uri).build();
            try {
                ResponseEntity<ScoreResponseBean> generateEntity2 =
                        restOperations.exchange(requestEntity, ScoreResponseBean.class);
                ScoreResponseBean score = generateEntity2.getBody();
                ScoreForm scoreForm = new ModelMapper().map(form, ScoreForm.class);
                if (scoreForm.getScore() > score.getScore()) {
                    scoreForm.setName("");
                    scoreForm.setMemo("");
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
            } catch (RestClientException e) {
                e.printStackTrace();
                throw new SudokuApplicationException();
            }
        } else {
            // modelに詰め込みます。
            model.addAttribute("form", form);
            List<Integer> SELECT_NUMS = ESListWrapUtils.getSelectNum(form.getType());
            model.addAttribute("selectNums", SELECT_NUMS);
            MutableList<MutableList<String>> SELECT_CELLS = ESListWrapUtils.createCells(form.getType());
            model.addAttribute("selectCells", SELECT_CELLS);
            return 3;
        }
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public void bestScore(final RestOperations restOperations, final LogicHandleBean handleBean)
            throws SudokuApplicationException {

        ScoreForm form = (ScoreForm) handleBean.getForm();
        if (form == null) {
            throw new SudokuApplicationException();
        }
        Model model = handleBean.getModel();
        if (model == null) {
            throw new SudokuApplicationException();
        }
        Optional<Long> noOpt = Optional.ofNullable(updateScore(restOperations, handleBean));
        if (noOpt.isPresent()) {
            // modelに詰め込みます。
            model.addAttribute("scoreForm", form);
            model.addAttribute("message", "登録完了です。");
            model.addAttribute("no", noOpt.get());
            List<RecordBean> list = new ArrayList<>();
            RecordBean bean = new RecordBean();
            bean.setNo(noOpt.get());
            bean.setType(form.getType());
            bean.setKeyHash(form.getKeyHash());
            bean.setScore(form.getScore());
            bean.setName(form.getName());
            bean.setMemo(form.getMemo());
            list.add(bean);
            model.addAttribute("list", list);
        } else {
            model.addAttribute("message", "更新失敗です。");
            log.debug("insertAnswerAndScore()でロールバックしています。");
        }
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    private Long updateScore(final RestOperations restOperations, final LogicHandleBean handleBean)
            throws SudokuApplicationException {

        ScoreForm form = (ScoreForm) handleBean.getForm();
        if (form == null) {
            throw new SudokuApplicationException();
        }
        UpdateSudokuScoreRequestBean request =
                new ModelMapper().map(form, UpdateSudokuScoreRequestBean.class);
        try {
            URI uri = new URI("http://localhost:8085/SudokuApi/updateMaster/score/");
            RequestEntity requestEntity = RequestEntity.put(uri).body(request);
            ResponseEntity<Long> generateEntity = restOperations.exchange(requestEntity, Long.class);
            return generateEntity.getBody();
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
