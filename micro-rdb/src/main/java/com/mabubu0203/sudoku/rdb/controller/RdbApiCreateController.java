package com.mabubu0203.sudoku.rdb.controller;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 検索する為のcontrollerです。<br>
 * このcontrollerを起点にエンドポイントが生成されます。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = {CommonConstants.SLASH + RestUrlConstants.URL_CREATE_MASTER + CommonConstants.SLASH},
        consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}

)
public class RdbApiCreateController {

    private final AnswerInfoService answerInfoService;
    private final ScoreInfoService scoreInfoService;

    /**
     * 数独の検索を実施します。<br>
     *
     * @param numberPlaceBean
     * @return true
     * @author uratamanabu
     * @since 1.0
     */
    @Transactional
    @PostMapping(value = {CommonConstants.EMPTY_STR})
    public ResponseEntity<String> insert(
            @RequestBody final NumberPlaceBean numberPlaceBean
    ) {

        try {
            AnswerInfoTbl answerInfoTbl = answerInfoService.insert(numberPlaceBean);
            scoreInfoService.insert(numberPlaceBean);
            return new ResponseEntity<>(answerInfoTbl.getKeyHash(), HttpStatus.OK);
        } catch (Exception e) {
            log.debug("一意制約違反です。");
            return new ResponseEntity<>(CommonConstants.EMPTY_STR, HttpStatus.CONFLICT);
        }

    }

}
