package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.interfaces.response.SearchSudokuRecordResponseBean;
import org.springframework.http.ResponseEntity;

/**
 * 検索する為のinterfaceです。<br>
 * このinterfaceを経由してロジックを実行してください。<br>
 * 実装については実装クラスを参照してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SearchService {

    /**
     * {@code answerKey}で数独の存在確認をします。<br>
     * 存在結果をtrue/falseで返却します。<br>
     * ・200:true/false<br>
     *
     * @param answerKey
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<Boolean> isExist(final String answerKey);

    /**
     * 検索画面からの検索をpageオブジェクトに格納し返却します。<br>
     * ・200:正常時<br>
     * ・204:0件時<br>
     *
     * @param conditionBean
     * @param pageNumber
     * @param pageSize
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<SearchSudokuRecordResponseBean> search(
            final SearchConditionBean conditionBean,
            final int pageNumber,
            final int pageSize);

    /**
     * {@code type}より数独を取得します。<br>
     * ・200:正常時<br>
     * ・204:0件時<br>
     *
     * @param type
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type);

    /**
     * {@code type}と{@code keyHash}より数独を取得します。<br>
     * ・200:正常時<br>
     * ・204:0件時<br>
     *
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type, final String keyHash);

    /**
     * {@code type}と{@code keyHash}より数独のスコアを取得します。<br>
     * ・200:正常時<br>
     * ・204:0件時<br>
     *
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<ScoreResponseBean> getScore(final int type, final String keyHash);

}
