package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import org.springframework.http.ResponseEntity;

/**
 * 生成する為のinterfaceです。<br>
 * このinterfaceを経由してロジックを実行してください。<br>
 * 実装については実装クラスを参照してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface CreateService {

    /**
     * typeから数独を生成します。<br>
     * 生成した数独を返却します。<br>
     * ・201:正常時<br>
     * ・204:異常時<br>
     *
     * @param type
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> generate(final int type);

    /**
     * 数独をRDBに保存します。<br>
     * 登録した{@code keyHash}を返却します。<br>
     * ・200:正常時<br>
     * ・409:一意制約違反<br>
     *
     * @param numberPlaceBean 数独
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<String> insertAnswerAndScore(final NumberPlaceBean numberPlaceBean);

}
