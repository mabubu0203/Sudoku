package com.mabubu0203.sudoku.rdb.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.rdb.domain.AnswerInfoTbl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

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
     * ANSWER_INFO_TBLへ数独をインサートします。
     *
     * @param numberplaceBean 数独
     * @return AnswerInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    AnswerInfoTbl insert(NumberPlaceBean numberplaceBean) ;

    /**
     * ANSWER_INFO_TBLへAnswerKeyで検索を行います。
     *
     * @param numberplaceBean 数独
     * @return List
     * @author uratamanabu
     * @since 1.0
     */
    List<AnswerInfoTbl> select(NumberPlaceBean numberplaceBean) ;

    /**
     * ANSWER_INFO_TBLへanswerKeyで検索を行います。
     *
     * @param answerKey answerKey
     * @return List
     * @author uratamanabu
     * @since 1.0
     */
    List<AnswerInfoTbl> findByAnswerKey(String answerKey);

    /**
     * ANSWER_INFO_TBLへTypeで検索を行います。
     *
     * @param type タイプ
     * @return AnswerInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    AnswerInfoTbl findByType(int type);

    /**
     * ANSWER_INFO_TBLへTypeとKeyHashで検索を行います。
     *
     * @param type    タイプ
     * @param keyHash KeyHash
     * @return AnswerInfoTbl
     * @author uratamanabu
     * @since 1.0
     */
    AnswerInfoTbl findByTypeAndKeyHash(int type, String keyHash);

    /**
     * ANSWER_INFO_TBLとScore_INFO_TBLの結合テーブルへ検索画面から入力された条件で検索します。
     *
     * @param condition 検索条件
     * @param pageable ページ情報
     * @author uratamanabu
     * @since 1.0
     */
    Page<AnswerInfoTbl> findRecords(SearchConditionBean condition, Pageable pageable);

}
