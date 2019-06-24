package com.mabubu0203.sudoku.logic;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SudokuModule {

    /**
     * 数独を0から生成し返却します。<br>
     *
     * @param type
     * @return NumberPlaceBean
     * @since 1.0
     */
    NumberPlaceBean generate(int type);

    /**
     * 数独をマスキングして返却します。<br>
     *
     * @param type
     * @param bean
     * @param level
     * @return NumberPlaceBean
     * @since 1.0
     */
    NumberPlaceBean filteredOfLevel(int type, NumberPlaceBean bean, String level);

}
