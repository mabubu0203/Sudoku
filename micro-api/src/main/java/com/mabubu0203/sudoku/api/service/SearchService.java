package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.interfaces.response.SearchSudokuRecordResponseBean;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SearchService {

    /**
     * answerKeyで数独の存在確認をします。<br>
     *
     * @param answerKey
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<Boolean> isExist(final String answerKey);

    /**
     * 検索画面からの検索をpageオブジェクトに格納し返却します。<br>
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
     *
     * @param type
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type);

    /**
     *
     * @param type
     * @param keyHash
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type, final String keyHash);

    /**
     *
     * @param type
     * @param keyHash
     * @param facade
     * @return ResponseEntity
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<ScoreResponseBean> getScore(final int type, final String keyHash, final MapperFacade facade);

}
