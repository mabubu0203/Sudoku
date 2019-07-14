package com.mabubu0203.sudoku.api.service.impl;

import com.mabubu0203.sudoku.api.service.UpdateService;
import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import com.mabubu0203.sudoku.rdb.service.ScoreInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 更新する為のサービスクラスです。<br>
 * このクラスを経由してロジックを実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class UpdateServiceImpl implements UpdateService {

    private final ScoreInfoService scoreInfoService;

    @Override
    @Transactional
    public ResponseEntity<Long> updateScore(
            final ScoreInfoTbl updateScoreBean, final int type, final String keyHash) {

        // TODO:scoreRepository.findByTypeAndKeyHash(type, keyHash)
        Optional<ScoreInfoTbl> scoreInfoTblOpt = scoreInfoService.findByTypeAndKeyHash(type, keyHash);
        if (scoreInfoTblOpt.isPresent()) {
            ScoreInfoTbl scoreInfoTbl = scoreInfoTblOpt.get();
            scoreInfoTbl.setName(updateScoreBean.getName());
            scoreInfoTbl.setMemo(updateScoreBean.getMemo());
            scoreInfoTbl.setScore(updateScoreBean.getScore());
            try {
                // TODO:scoreRepository.save(scoreInfoTbl)
                scoreInfoTbl = scoreInfoService.update(scoreInfoTbl);
                return new ResponseEntity<>(scoreInfoTbl.getNo(), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(Long.MIN_VALUE, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Long.MIN_VALUE, HttpStatus.BAD_REQUEST);
        }
    }

}
