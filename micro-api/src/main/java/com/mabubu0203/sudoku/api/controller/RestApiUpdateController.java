package com.mabubu0203.sudoku.api.controller;

import com.mabubu0203.sudoku.api.service.UpdateService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.controller.RestBaseController;
import com.mabubu0203.sudoku.interfaces.request.UpdateSudokuScoreRequestBean;
import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(
        value = {CommonConstants.SLASH + RestUrlConstants.URL_UPDATE_MASTER + CommonConstants.SLASH},
        produces = "application/json"
)
public class RestApiUpdateController extends RestBaseController {

    private final UpdateService service;
    private final MapperFacade orikaMapperFacade;

    /**
     * コンストラクタ<br>
     *
     * @param service
     * @param orikaMapperFacade
     * @author uratamanabu
     * @since 1.0
     */
    public RestApiUpdateController(UpdateService service, MapperFacade orikaMapperFacade) {
        this.service = service;
        this.orikaMapperFacade = orikaMapperFacade;
    }

    /**
     * スコアをRDBに保存します。<br>
     *
     * @param request
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    @PutMapping(value = {RestUrlConstants.URL_SCORE})
    public ResponseEntity<Long> updateScore(
            @RequestBody @Validated final UpdateSudokuScoreRequestBean request) {

        log.info("updateScore");
        ScoreInfoTbl updateScoreBean = orikaMapperFacade.map(request, ScoreInfoTbl.class);
        return service.updateScore(updateScoreBean, request.getType(), request.getKeyHash());
    }

}
