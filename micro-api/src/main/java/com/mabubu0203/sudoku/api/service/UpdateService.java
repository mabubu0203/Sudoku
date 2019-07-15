package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

/**
 * 更新する為のinterfaceです。<br>
 * このinterfaceを経由してロジックを実行してください。<br>
 * 実装については実装クラスを参照してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface UpdateService {

    /**
     * スコアをRDBに保存します。<br>
     * 更新した{@code no}を返却します。<br>
     * ・200:正常時<br>
     * ・400:異常時<br>
     *
     * @param restOperations
     * @param updateScoreBean
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<Long> updateScore(
            final RestOperations restOperations,
            final ScoreInfoTbl updateScoreBean,
            final int type,
            final String keyHash);

}
