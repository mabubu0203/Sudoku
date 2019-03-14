package com.mabubu0203.sudoku.api.service;

import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import org.springframework.http.ResponseEntity;

public interface CreateService {

    /**
     * typeから数独を生成します。
     *
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<NumberPlaceBean> generate(final int type) throws SudokuApplicationException;

    /**
     * 数独をRDBに保存します。
     *
     * @author uratamanabu
     * @since 1.0
     */
    ResponseEntity<String> insertAnswerAndScore(final NumberPlaceBean numberPlaceBean);

}
