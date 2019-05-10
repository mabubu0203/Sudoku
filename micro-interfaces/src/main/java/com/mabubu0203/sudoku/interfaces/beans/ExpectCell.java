package com.mabubu0203.sudoku.interfaces.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 数独の予想セルを表現します。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Deprecated
public class ExpectCell implements Serializable {

    private static final long serialVersionUID = 1L;

    private int value;

    private Set<Integer> expectValues;

    public ExpectCell(int value) {
        this.value = value;
        this.expectValues = new HashSet<>();
    }

    public void addExpectValues(int value) {
        this.expectValues.add(value);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
