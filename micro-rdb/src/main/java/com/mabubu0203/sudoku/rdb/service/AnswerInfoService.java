package com.mabubu0203.sudoku.rdb.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.stream.Stream;

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
     * @author uratamanabu
     * @since 1.0
     */
    AnswerInfoTbl insert(NumberPlaceBean numberplaceBean);

    /**
     * ANSWER_INFO_TBLへanswerKeyで検索を行います。<br>
     * 返却はN件を表すStreamで返却します。<br>
     *
     * @param answerKey answerKey
     * @return N件
     * @author uratamanabu
     * @since 1.0
     */
    Stream<AnswerInfoTbl> searchByAnswerKey(String answerKey);

    /**
     * ANSWER_INFO_TBLへTypeで検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type タイプ
     * @return 0/1件
     * @author uratamanabu
     * @since 1.0
     */
    Optional<AnswerInfoTbl> findFirstByType(Integer type);

    /**
     * ANSWER_INFO_TBLへTypeとKeyHashで検索を行います。<br>
     * 返却は0/1件を表すOptionalで返却します。<br>
     *
     * @param type    タイプ
     * @param keyHash KeyHash
     * @return 0/1件
     * @author uratamanabu
     * @since 1.0
     */
    Optional<AnswerInfoTbl> findByTypeAndKeyHash(Integer type, String keyHash);

    /**
     * ANSWER_INFO_TBLとScore_INFO_TBLの結合テーブルへ検索画面から入力された条件で検索します。<br>
     * 返却はN件を表すPageで返却します。<br>
     *
     * @param condition 検索条件
     * @param pageable  ページ情報
     * @return N件
     * @author uratamanabu
     * @since 1.0
     */
    Page<AnswerInfoTbl> searchRecords(SearchConditionBean condition, Pageable pageable);

    /**
     * EntityをBeanに変換して返却します。<br>
     * .
     *
     * @param answerInfoTbl
     * @return NumberPlaceBean
     * @author uratamanabu
     * @since 1.0
     */
    NumberPlaceBean answerInfoTblConvertBean(AnswerInfoTbl answerInfoTbl);

}
