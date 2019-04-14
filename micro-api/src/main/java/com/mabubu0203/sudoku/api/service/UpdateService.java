package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;
import org.springframework.http.ResponseEntity;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface UpdateService {

    /**
     * <br>
     *
     * @param updateScoreBean
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<Long> updateScore(final ScoreInfoTbl updateScoreBean, final int type, final String keyHash);

}
