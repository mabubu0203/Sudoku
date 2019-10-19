package com.mabubu0203.sudoku.rdb.controller;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.projection.SearchResult;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Objects;

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
    public ResponseEntity<PagedModel<EntityModel<SearchResult>>> search(
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
        condition.setType(selectType);
        condition.setDateStart(dateStart);
        condition.setDateEnd(dateEnd);
        if (Objects.nonNull(selectorNo)) {
            condition.setNo(no);
            condition.setSelectorNo(selectorNo);
        }
        if (Objects.nonNull(selectorScore)) {
            condition.setScore(score);
            condition.setSelectorScore(selectorScore);
        }
        if (Objects.nonNull(selectorKeyHash)) {
            condition.setKeyHash(keyHash);
            condition.setSelectorKeyHash(selectorKeyHash);
        }
        if (Objects.nonNull(selectorName)) {
            condition.setName(name);
            condition.setSelectorName(selectorName);
        }
        return new ResponseEntity<>(answerInfoService.searchRecords(condition, pageable), HttpStatus.OK);
    }

}
