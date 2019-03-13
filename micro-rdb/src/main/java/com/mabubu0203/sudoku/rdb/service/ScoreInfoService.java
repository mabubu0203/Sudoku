package com.mabubu0203.sudoku.rdb.service;

import com.mabubu0203.sudoku.rdb.domain.ScoreInfoTbl;

/**
 * SCORE_INFO_TBLへのinterfaceです。<br>
 * このinterfaceを経由してCRUD操作を実行してください。<br>
 * 実装については実装クラスを参照してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface ScoreInfoService {

    /**
     * SCORE_INFO_TBLへTypeとKeyHashで検索を行います。
     *
     * @param type    タイプ
     * @param keyHash KeyHash
     * @return ScoreInfoTbl
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    ScoreInfoTbl findByTypeAndKeyHash(int type, String keyHash);

    /**
     * SCORE_INFO_TBLへスコア情報をアップデートします。
     *
     * @param scoreInfoTbl スコア情報
     * @return ScoreInfoTbl
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    ScoreInfoTbl update(ScoreInfoTbl scoreInfoTbl);

}
