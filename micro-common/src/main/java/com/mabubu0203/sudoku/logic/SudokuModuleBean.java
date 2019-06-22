package com.mabubu0203.sudoku.logic;

import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import lombok.Data;

@Data
public class SudokuModuleBean {

    private int size;

    private int[][] numberPlaceArray;

    private NumberPlaceBean numberPlaceBean;

    /**
     * コンストラクタです。
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    public SudokuModuleBean(int type) {
        this.size = type;
        this.numberPlaceArray = new int[this.size][this.size];
        this.numberPlaceBean = new NumberPlaceBean();
    }

}
