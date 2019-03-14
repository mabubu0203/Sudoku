package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.UpdateService;
import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UpdateServiceImpl implements UpdateService {

    /**
     * ScoreInfoServiceを配備します。
     */
    private final ScoreInfoService scoreInfoService;

    public UpdateServiceImpl(ScoreInfoService scoreInfoService) {
        this.scoreInfoService = scoreInfoService;
    }

    @Override
    @Transactional
    public ResponseEntity<Long> updateScore(
            final ScoreInfoTbl updateScoreBean, final int type, final String keyHash) {

        ScoreInfoTbl scoreInfoTbl = scoreInfoService.findByTypeAndKeyHash(type, keyHash);
        scoreInfoTbl.setName(updateScoreBean.getName());
        scoreInfoTbl.setMemo(updateScoreBean.getMemo());
        scoreInfoTbl.setScore(updateScoreBean.getScore());
        try {
            scoreInfoTbl = scoreInfoService.update(scoreInfoTbl);
            return new ResponseEntity<>(scoreInfoTbl.getNo(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Long.MIN_VALUE, HttpStatus.BAD_REQUEST);
        }
    }

}
