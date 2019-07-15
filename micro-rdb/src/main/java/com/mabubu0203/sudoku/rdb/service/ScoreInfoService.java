package com.mabubu0203.sudoku.rdb.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;

import java.util.Optional;

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
     * SCORE_INFO_TBLへ空スコア情報をインサートします。<br>
     *
     * @param numberplaceBean 数独
     * @return ScoreInfoTbl
     * @since 1.0
     */
    ScoreInfoTbl insert(NumberPlaceBean numberplaceBean);

    /**
     * SCORE_INFO_TBLへTypeとKeyHashで検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type    タイプ
     * @param keyHash KeyHash
     * @return 0/1件
     * @since 1.0
     */
    Optional<ScoreInfoTbl> findByTypeAndKeyHash(Integer type, String keyHash);

    /**
     * SCORE_INFO_TBLへスコア情報をアップデートします。<br>
     *
     * @param numberplaceBean 数独
     * @return ScoreInfoTbl
     * @since 1.0
     */
    ScoreInfoTbl update(NumberPlaceBean numberplaceBean);

    /**
     * SCORE_INFO_TBLへスコア情報をアップデートします。<br>
     *
     * @param scoreInfoTbl スコア情報
     * @return ScoreInfoTbl
     * @since 1.0
     */
    ScoreInfoTbl update(ScoreInfoTbl scoreInfoTbl);

}
