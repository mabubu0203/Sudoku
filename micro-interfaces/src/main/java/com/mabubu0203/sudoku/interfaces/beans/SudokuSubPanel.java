package com.mabubu0203.sudoku.interfaces.beans;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.*;

/**
 * 数独のパネルを表現します。<br>
 * ・{@code size=2}の場合、セルを4箇所保持します。<br>
 * ・{@code size=3}の場合、セルを9箇所保持します。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class SudokuSubPanel implements Serializable {

    private static final long serialVersionUID = 1L;
    // (row,col)の座標のリスト
    private List<Pair<Integer, Integer>> coordinates;
    private Map<Pair<Integer, Integer>, SudokuCell> cellMap;
    private boolean confirmFlg;

    public SudokuSubPanel(final int size) {
        coordinates = new ArrayList<>();
        cellMap = new HashMap<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Pair<Integer, Integer> coordinate = getCoordinate(col, row);
                coordinates.add(coordinate);
                cellMap.put(coordinate, new SudokuCell(0));
            }
        }
        confirmFlg = false;
    }

    public Pair<Integer, Integer> getCoordinate(int row, int col) {
        return Pair.of(row, col);
    }

    public SudokuCell findCell(int row, int col) {
        return findCell(Pair.of(row, col));
    }

    private SudokuCell findCell(Pair<Integer, Integer> coordinate) {
        return cellMap.get(coordinate);
    }

    @Deprecated
    public List<SudokuCell> findCellByRow(int size, int row) {
        List<SudokuCell> result = new ArrayList<>();
        for (int col = 0; col < size; col++) {
            Pair<Integer, Integer> coordinate = getCoordinate(row, col);
            result.add(cellMap.get(coordinate));
        }
        return result;
    }

    @Deprecated
    public List<SudokuCell> findCellByCol(int size, int col) {
        List<SudokuCell> result = new ArrayList<>();
        for (int row = 0; row < size; col++) {
            Pair<Integer, Integer> coordinate = getCoordinate(row, col);
            result.add(cellMap.get(coordinate));
        }
        return result;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        cellMap.forEach((key, value) -> sj.add(key + ":" + value));
        return sj.toString();
    }

}
