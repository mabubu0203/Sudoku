package com.mabubu0203.sudoku.rdb.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.domain.ScoreInfoTbl;

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

}
