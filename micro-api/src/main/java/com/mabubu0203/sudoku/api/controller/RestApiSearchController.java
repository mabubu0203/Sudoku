package com.mabubu0203.sudoku.api.controller;

import com.mabubu0203.sudoku.api.service.SearchService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.PathParameterConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.controller.RestBaseController;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.validator.constraint.AnswerKey;
import com.mabubu0203.sudoku.validator.constraint.KeyHash;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        produces = "application/json"
)
public class RestApiSearchController extends RestBaseController {

    private final SearchService service;

    private final MapperFacade orikaMapperFacade;

    public RestApiSearchController(SearchService service, MapperFacade orikaMapperFacade) {
        this.service = service;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    // TODO:移植中
//  /**
//   * 数独の検索を実施します。
//   *
//   * @author uratamanabu
//   * @version 1.0
//   * @since 1.0
//   */
//  @PostMapping(value = {CommonConstants.EMPTY_STR})
//  public ResponseEntity<SearchSudokuRecordResponseBean> search(
//      @RequestBody @Validated final SearchSudokuRecordRequestBean request) {
//
//    log.info("search");
//    Pageable pageable =
//        new PageRequest(
//            request.getPageNumber(), request.getPageSize(), new OrderBySource("NoAsc").toSort());
//    SearchConditionBean conditionBean = orikaMapperFacade.map(request, SearchConditionBean.class);
//    conditionBean.setType(request.getSelectType());
//    return service.search(conditionBean, pageable);
//  }

    /**
     * 数独の存在確認をします。
     *
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

    @GetMapping(value = {RestUrlConstants.URL_SUDOKU})
    public ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(
            @RequestParam(name = "type") @Type(message = "数値1桁を入力しましょう。") final int type,
            @RequestParam(name = "keyHash") @KeyHash(message = "数値64桁を入力しましょう。") final String keyHash) {

        log.info("getNumberPlaceDetail");
        if (keyHash.isEmpty()) {
            return service.getNumberPlaceDetail(type);
        } else {
            return service.getNumberPlaceDetail(type, keyHash);
        }
    }

    @GetMapping(value = {RestUrlConstants.URL_SCORE})
    public ResponseEntity<ScoreResponseBean> getScore(
            @RequestParam(name = "type") @Type(message = "数値1桁を入力しましょう。") final int type,
            @RequestParam(name = "keyHash") @KeyHash(message = "数値64桁を入力しましょう。") final String keyHash) {

        log.info("getScore");
        return service.getScore(type, keyHash, orikaMapperFacade);
    }

}
