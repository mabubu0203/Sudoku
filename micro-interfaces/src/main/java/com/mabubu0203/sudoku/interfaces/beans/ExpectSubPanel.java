package com.mabubu0203.sudoku.interfaces.beans;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.*;

/**
 * 数独の予想パネルを表現します。<br>
 * ・{@code size=2}の場合、セルを4箇所保持します。<br>
 * ・{@code size=3}の場合、セルを9箇所保持します。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class ExpectSubPanel implements Serializable {

    private static final long serialVersionUID = 1L;
    // (row,col)の座標のリスト
    private List<Pair<Integer, Integer>> coordinates;
    private Map<Pair<Integer, Integer>, ExpectCell> cellMap;
    private boolean confirmFlg;

    private List<Set<Integer>> expectRowList;
    private List<Set<Integer>> expectColList;

    public ExpectSubPanel(final int size) {
        this.coordinates = new ArrayList<>();
        this.expectRowList = new ArrayList<>();
        this.expectColList = new ArrayList<>();
        this.cellMap = new HashMap<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Pair<Integer, Integer> coordinate = getCoordinate(col, row);
                this.coordinates.add(coordinate);
                this.cellMap.put(coordinate, new ExpectCell(0));
            }
        }
        confirmFlg = false;
    }

    public Pair<Integer, Integer> getCoordinate(int row, int col) {
        return Pair.of(row, col);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        this.cellMap.forEach((key, value) -> sj.add(key + ":" + value));
        return sj.toString();
    }

}
