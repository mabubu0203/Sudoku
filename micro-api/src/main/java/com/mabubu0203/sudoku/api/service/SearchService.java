package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

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
     * @param restOperations
     * @param answerKey
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<Boolean> isExist(final RestOperations restOperations, final String answerKey);

    /**
     * {@code type}より数独を取得します。<br>
     * ・200:正常時<br>
     * ・204:0件時<br>
     *
     * @param restOperations
     * @param type
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final RestOperations restOperations,
                                                         final int type);

    /**
     * {@code type}と{@code keyHash}より数独を取得します。<br>
     * ・200:正常時<br>
     * ・204:0件時<br>
     *
     * @param restOperations
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final RestOperations restOperations,
                                                         final int type, final String keyHash);

    /**
     * {@code type}と{@code keyHash}より数独のスコアを取得します。<br>
     * ・200:正常時<br>
     * ・204:0件時<br>
     *
     * @param restOperations
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<ScoreResponseBean> getScore(final RestOperations restOperations,
                                               final int type, final String keyHash);

}
