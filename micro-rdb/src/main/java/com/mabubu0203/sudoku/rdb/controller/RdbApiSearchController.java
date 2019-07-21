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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
     * @param pageable 検索条件
     * @return Page
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(value = {CommonConstants.EMPTY_STR})
    public Page<AnswerInfoTbl> search(
            @RequestParam(value = "selectType") Integer selectType,
            @RequestParam(value = "no", required = false) Long no,
            @RequestParam(value = "keyHash", required = false) String keyHash,
            @RequestParam(value = "score", required = false) Integer score,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dateStart", required = false) LocalDate dateStart,
            @RequestParam(value = "dateEnd", required = false) LocalDate dateEnd,
            @RequestParam(value = "selectorNo") Integer selectorNo,
            @RequestParam(value = "selectorScore") Integer selectorScore,
            @RequestParam(value = "selectorKeyHash") Integer selectorKeyHash,
            @RequestParam(value = "selectorName") Integer selectorName,
            @PageableDefault(sort = {"no"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {

        SearchConditionBean condition = new SearchConditionBean();
        condition.setSelectorNo(selectorNo);
        condition.setSelectorScore(selectorScore);
        condition.setSelectorKeyHash(selectorKeyHash);
        condition.setSelectorName(selectorName);
        return answerInfoService.searchRecords(condition, pageable);
    }

}
