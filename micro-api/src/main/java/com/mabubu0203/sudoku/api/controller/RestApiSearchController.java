package com.mabubu0203.sudoku.api.controller;

import com.mabubu0203.sudoku.api.service.SearchService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.PathParameterConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.controller.RestBaseController;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.request.SearchSudokuRecordRequestBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.interfaces.response.SearchSudokuRecordResponseBean;
import com.mabubu0203.sudoku.validator.constraint.AnswerKey;
import com.mabubu0203.sudoku.validator.constraint.KeyHash;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(
        value = {CommonConstants.SLASH + RestUrlConstants.URL_SEARCH_MASTER + CommonConstants.SLASH},
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}

)
public class RestApiSearchController extends RestBaseController {

    private final SearchService service;
    private final MapperFacade facade;

    /**
     * コンストラクタ<br>
     *
     * @param service
     * @param facade
     * @author uratamanabu
     * @since 1.0
     */
    public RestApiSearchController(final SearchService service, final MapperFacade facade) {
        this.service = service;
        this.facade = facade;
    }

    /**
     * 数独の検索を実施します。<br>
     *
     * @param request
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {CommonConstants.EMPTY_STR})
    public ResponseEntity<SearchSudokuRecordResponseBean> search(
            @RequestBody @Validated final SearchSudokuRecordRequestBean request) {

        log.info("search");
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.Direction.DESC);
        SearchConditionBean conditionBean = facade.map(request, SearchConditionBean.class);
        conditionBean.setType(request.getSelectType());
        return service.search(conditionBean, pageable);
    }

    /**
     * 数独の存在確認をします。<br>
     *
     * @param answerKey
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {PathParameterConstants.PATH_TYPEANSWER_KEY})
    public ResponseEntity<Boolean> isSudokuExist(
            @PathVariable(name = "answerKey")
            @AnswerKey(message = "数値64桁を入力しましょう。") final String answerKey) {

        log.info("isSudokuExist");
        return service.isExist(answerKey);
    }

    /**
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {RestUrlConstants.URL_SUDOKU})
    public ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(
            @RequestParam(name = "type") @Type(message = "数値1桁を入力しましょう。") final Integer type,
            @RequestParam(name = "keyHash", required = false) @KeyHash(message = "数値64桁を入力しましょう。") final String keyHash) {

        log.info("getNumberPlaceDetail");
        if (keyHash.isEmpty()) {
            return service.getNumberPlaceDetail(type.intValue());
        } else {
            return service.getNumberPlaceDetail(type.intValue(), keyHash);
        }
    }

    /**
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {RestUrlConstants.URL_SCORE})
    public ResponseEntity<ScoreResponseBean> getScore(
            @RequestParam(name = "type") @Type(message = "数値1桁を入力しましょう。") final Integer type,
            @RequestParam(name = "keyHash") @KeyHash(message = "数値64桁を入力しましょう。") final String keyHash) {

        log.info("getScore");
        return service.getScore(type.intValue(), keyHash, facade);
    }

}
