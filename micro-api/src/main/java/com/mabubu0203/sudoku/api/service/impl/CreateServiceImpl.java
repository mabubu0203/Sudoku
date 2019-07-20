package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.CreateService;
import com.mabubu0203.sudoku.clients.rdb.AnswerInfoTblEndpoints;
import com.mabubu0203.sudoku.clients.rdb.ScoreInfoTblsEndPoints;
import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.logic.SudokuModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.time.LocalDateTime;
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

    @Autowired
    private ScoreInfoTblsEndPoints scoreInfoTblsEndPoints;

    @Autowired
    private AnswerInfoTblEndpoints answerInfoTblEndpoints;

    private final SudokuModule sudokuModule;

    @Autowired
    @Qualifier("com.mabubu0203.sudoku.api.config.ModelMapperConfiguration.ModelMapper")
    private ModelMapper modelMapper;

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

        try {
            AnswerInfoTbl answerInfoTbl = modelMapper.map(numberPlaceBean, AnswerInfoTbl.class);
            answerInfoTbl.setCreateDate(LocalDateTime.now());
            answerInfoTblEndpoints.insert(restOperations, answerInfoTbl);
            ScoreInfoTbl scoreInfoTbl = modelMapper.map(numberPlaceBean, ScoreInfoTbl.class);
            scoreInfoTbl.setScore(CommonConstants.INTEGER_ZERO);
            scoreInfoTbl.setName(CommonConstants.EMPTY_STR);
            scoreInfoTbl.setMemo(CommonConstants.EMPTY_STR);
            scoreInfoTblsEndPoints.insert(restOperations, scoreInfoTbl);
            return new ResponseEntity<>(answerInfoTbl.getKeyHash(), HttpStatus.OK);
        } catch (Exception e) {
            log.debug("一意制約違反です。");
            return new ResponseEntity<>(CommonConstants.EMPTY_STR, HttpStatus.CONFLICT);
        }

    }

}
