package com.mabubu0203.sudoku.rdb.controller;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
        value = {CommonConstants.SLASH + RestUrlConstants.URL_CREATE_MASTER + CommonConstants.SLASH},
        consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}

)
public class RdbApiCreateController {
}
