package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.CreateService;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.logic.SudokuModule;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@AllArgsConstructor
@Service
public class CreateServiceImpl implements CreateService {

    private final AnswerInfoService answerInfoService;
    private final ScoreInfoService scoreInfoService;
    private final SudokuModule sudokuModule;

    @Override
    public ResponseEntity<NumberPlaceBean> generate(final int type) {

        Optional<NumberPlaceBean> numberPlaceBeanOpt = Optional.ofNullable(sudokuModule.generate(type));
        if (numberPlaceBeanOpt.isPresent()) {
            return new ResponseEntity<>(numberPlaceBeanOpt.get(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }

    @Transactional
    @Override
    public ResponseEntity<String> insertAnswerAndScore(final NumberPlaceBean numberPlaceBean) {

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
