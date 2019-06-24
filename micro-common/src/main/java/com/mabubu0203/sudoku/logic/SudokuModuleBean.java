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
     * @param type
     */
    public SudokuModuleBean(int type) {
        this.size = type;
        this.numberPlaceArray = new int[this.size][this.size];
        this.numberPlaceBean = new NumberPlaceBean();
    }

}
