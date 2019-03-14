package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import org.springframework.http.ResponseEntity;

public interface UpdateService {

    /**
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<Long> updateScore(final ScoreInfoTbl updateScoreBean, final int type, final String keyHash);

}
