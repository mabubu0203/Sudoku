package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.CreateService;
import com.mabubu0203.sudoku.clients.rdb.custom.RdbApiCreateEndPoints;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.logic.SudokuModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

/**
 * 生成する為のサービスクラスです。<br>
 * このクラスを経由してロジックを実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CreateServiceImpl implements CreateService {

    private final SudokuModule sudokuModule;
    private final RdbApiCreateEndPoints rdbApiCreateEndPoints;

    @Override
    public ResponseEntity<NumberPlaceBean> generate(final int type) {

        Optional<NumberPlaceBean> numberPlaceBeanOpt = Optional.ofNullable(sudokuModule.generate(type));
        if (numberPlaceBeanOpt.isPresent()) {
            return new ResponseEntity<>(numberPlaceBeanOpt.get(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }

    @Override
    public ResponseEntity<String> insertAnswerAndScore(final RestOperations restOperations, final NumberPlaceBean numberPlaceBean) {

        Optional<String> keyHashOpt = rdbApiCreateEndPoints.insert(restOperations, numberPlaceBean);
        if (keyHashOpt.isPresent()) {
            return new ResponseEntity<>(keyHashOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(CommonConstants.EMPTY_STR, HttpStatus.CONFLICT);
        }

    }

}
