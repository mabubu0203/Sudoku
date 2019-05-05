package com.mabubu0203.sudoku.interfaces.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * 数独のセルを表現します。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class SudokuCell implements Serializable {

    private static final long serialVersionUID = 1L;

    private int value;

    private boolean confirmFlg;

    public SudokuCell(int value) {
        this.value = value;
        confirmFlg = false;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
