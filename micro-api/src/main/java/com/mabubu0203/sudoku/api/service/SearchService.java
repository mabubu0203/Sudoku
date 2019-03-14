package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.SearchConditionBean;
import com.mabubu0203.sudoku.interfaces.response.ScoreResponseBean;
import com.mabubu0203.sudoku.interfaces.response.SearchSudokuRecordResponseBean;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SearchService {

    /**
     * answerKeyで数独の存在確認をします。
     *
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<Boolean> isExist(final String answerKey);

    /**
     * 検索画面からの検索をpageオブジェクトに格納し返却します。
     *
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<SearchSudokuRecordResponseBean> search(final SearchConditionBean conditionBean, final Pageable pageable);

    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type);

    ResponseEntity<NumberPlaceBean> getNumberPlaceDetail(final int type, final String keyHash);

    ResponseEntity<ScoreResponseBean> getScore(final int type, final String keyHash, final MapperFacade facade);

}
