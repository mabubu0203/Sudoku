package com.mabubu0203.sudoku.api.controller;


import com.mabubu0203.sudoku.api.service.CreateService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.PathParameterConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.controller.RestBaseController;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.request.ResisterSudokuRecordRequestBean;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 生成する為のcontrollerです。<br>
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
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class RestApiCreateController extends RestBaseController {

    private final CreateService service;

    /**
     * 指定した数独をRDBに保存します。<br>
     *
     * @param request
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @PostMapping(value = {RestUrlConstants.URL_GENERATE})
    public ResponseEntity<String> resisterSudoku(
            @RequestBody @Validated final ResisterSudokuRecordRequestBean request) {

        log.info("resisterSudoku");

        return service.insertAnswerAndScore(request.getNumberPlaceBean());
    }

    /**
     * 指定した{@code type}に従い数独を作成します。<br>
     *
     * @param type
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(
            value = {
                    RestUrlConstants.URL_GENERATE + CommonConstants.SLASH + PathParameterConstants.PATH_TYPE
            }
    )
    public ResponseEntity<NumberPlaceBean> generateSudoku(
            @PathVariable(name = "type") @Type final Integer type) {

        log.info("generateSudoku");
        return service.generate(type);
    }

}
