package com.mabubu0203.sudoku.api.controller;

import com.mabubu0203.sudoku.api.service.UpdateService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.constants.RestUrlConstants;
import com.mabubu0203.sudoku.controller.RestBaseController;
import com.mabubu0203.sudoku.interfaces.request.UpdateSudokuScoreRequestBean;
import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 更新する為のcontrollerです。<br>
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
        value = {CommonConstants.SLASH + RestUrlConstants.URL_UPDATE_MASTER + CommonConstants.SLASH},
        consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
)
public class RestApiUpdateController extends RestBaseController {

    private final UpdateService service;
    private final ModelMapper modelMapper;

    /**
     * 指定したスコアをRDBに保存します。<br>
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
        ScoreInfoTbl updateScoreBean = modelMapper.map(request, ScoreInfoTbl.class);
        return service.updateScore(updateScoreBean, request.getType().intValue(), request.getKeyHash());
    }

}
