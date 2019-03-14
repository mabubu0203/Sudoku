package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.CreateService;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.logic.Sudoku;
import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.rdb.service.AnswerInfoService;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CreateServiceImpl implements CreateService {

    /**
     * AnswerInfoServiceを配備します。
     */
    private final AnswerInfoService answerInfoService;

    /**
     * ScoreInfoServiceを配備します。
     */
    private final ScoreInfoService scoreInfoService;

    public CreateServiceImpl(AnswerInfoService answerInfoService, ScoreInfoService scoreInfoService) {
        this.answerInfoService = answerInfoService;
        this.scoreInfoService = scoreInfoService;
    }

    @Override
    public ResponseEntity<NumberPlaceBean> generate(final int type)
            throws SudokuApplicationException {

        Optional<NumberPlaceBean> numberPlaceBeanOpt = Optional.ofNullable(new Sudoku(type).generate());
        if (numberPlaceBeanOpt.isPresent()) {
            return new ResponseEntity<>(numberPlaceBeanOpt.get(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new NumberPlaceBean(), HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
    }

}
