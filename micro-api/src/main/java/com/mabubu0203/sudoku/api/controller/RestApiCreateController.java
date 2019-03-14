package com.mabubu0203.sudoku.api.controller;


import com.mabubu0203.sudoku.api.service.CreateService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.PathParameterConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.controller.RestBaseController;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.request.ResisterSudokuRecordRequestBean;
import com.mabubu0203.sudoku.validator.constraint.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(
        value = {CommonConstants.SLASH + RestUrlConstants.URL_CREATE_MASTER + CommonConstants.SLASH},
        produces = "application/json"
)
public class RestApiCreateController extends RestBaseController {

    private final CreateService service;

    public RestApiCreateController(CreateService service) {
        this.service = service;
    }

    /**
     * 数独をRDBに保存します。
     *
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
     * タイプに従い数独を作成します。
     *
     * @author uratamanabu
     * @since 1.0
     */
    @GetMapping(
            value = {
                    RestUrlConstants.URL_GENERATE + CommonConstants.SLASH + PathParameterConstants.PATH_TYPE
            }
    )
    public ResponseEntity<NumberPlaceBean> generateSudoku(
            @PathVariable(name = "type") @Type final int type) {

        log.info("generateSudoku");
        return service.generate(type);
    }

}
