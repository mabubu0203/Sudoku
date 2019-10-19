package com.mabubu0203.sudoku.rdb.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.domain.AnswerInfoTbl;
import com.mabubu0203.sudoku.interfaces.projection.SearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

/**
 * ANSWER_INFO_TBLへのinterfaceです。<br>
 * このinterfaceを経由してCRUD操作を実行してください。<br>
 * 実装については実装クラスを参照してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface AnswerInfoService {

    /**
     * ANSWER_INFO_TBLへ数独をインサートします。<br>
     *
     * @param numberplaceBean 数独
     * @return AnswerInfoTbl
     * @since 1.0
     */
    AnswerInfoTbl insert(NumberPlaceBean numberplaceBean);

    /**
     * ANSWER_INFO_TBLとScore_INFO_TBLの結合テーブルへ検索画面から入力された条件で検索します。<br>
     * 返却はN件を表す{@code PagedResources}で返却します。<br>
     *
     * @param condition 検索条件
     * @param pageable  ページ情報
     * @return N件
     * @since 1.0
     */
    PagedModel<EntityModel<SearchResult>> searchRecords(SearchConditionBean condition, Pageable pageable);

}
