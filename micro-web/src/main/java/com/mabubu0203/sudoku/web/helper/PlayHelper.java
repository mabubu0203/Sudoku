package com.mabubu0203.sudoku.web.helper;

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
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(form.getSelectType()));
        uriVariables.put("keyHash", "");
        URI uri = new UriTemplate(sudokuUriApi + "searchMaster/sudoku?type={type}&keyHash={keyHash}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build();
        try {
            ResponseEntity<NumberPlaceBean> generateEntity =
                    restOperations.exchange(requestEntity, NumberPlaceBean.class);
            NumberPlaceBean numberPlaceBean = generateEntity.getBody();
            // 一件から虫食いのリストを取得します。
            numberPlaceBean =
                    sudokuModule.filteredOfLevel(form.getSelectType(), numberPlaceBean, form.getSelectLevel());
            PlayForm playForm = new ModelMapper().map(numberPlaceBean, PlayForm.class);
            playForm.setCount(0);
            playForm.setScore(SudokuUtils.calculationScore(form.getSelectType(), form.getSelectLevel()));
            model.addAttribute("playForm", playForm);
            model.addAttribute("selectNums", ESListWrapUtils.getSelectNum(form.getSelectType()));
            model.addAttribute("selectCells", ESListWrapUtils.createCells(form.getSelectType()));
        } catch (RestClientException e) {
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
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("type", Integer.toString(form.getType()));
        uriVariables.put("keyHash", form.getKeyHash());
        URI uri = new UriTemplate(sudokuUriApi + "searchMaster/sudoku?type={type}&keyHash={keyHash}").expand(uriVariables);
        RequestEntity requestEntity =
                RequestEntity
                        .get(uri)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build();
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
            uri = new UriTemplate(sudokuUriApi + "searchMaster/score?type={type}&keyHash={keyHash}").expand(uriVariables);
            requestEntity =
                    RequestEntity
                            .get(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .build();
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
        try {
            URI uri = new URI(sudokuUriApi + "updateMaster/score/");
            RequestEntity requestEntity =
                    RequestEntity
                            .put(uri)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .body(request);
            ResponseEntity<Long> generateEntity = restOperations.exchange(requestEntity, Long.class);
            return generateEntity.getBody();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            switch (status) {
                case BAD_REQUEST:
                    log.info("???");
                    log.info(e.getMessage());
                default:
                    return null;
            }
        }
    }

}
