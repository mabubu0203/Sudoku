package com.mabubu0203.sudoku.rdb.controller;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
@AllArgsConstructor
@RestController
@RequestMapping(
        value = {CommonConstants.SLASH + RestUrlConstants.URL_SEARCH_MASTER + CommonConstants.SLASH},
        consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}

)
public class RdbApiSearchController {

    private final AnswerInfoService answerInfoService;

    /**
     * 数独の検索を実施します。<br>
     *
     * @param conditionBean 検索条件
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {CommonConstants.EMPTY_STR})
    public Page<AnswerInfoTbl> search(
            @RequestBody final SearchConditionBean conditionBean, final Pageable pageable) {
        return answerInfoService.searchRecords(conditionBean, pageable);
    }

}
