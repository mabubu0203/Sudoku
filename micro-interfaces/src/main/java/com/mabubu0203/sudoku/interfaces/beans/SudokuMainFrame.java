package com.mabubu0203.sudoku.interfaces.beans;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.*;

/**
 * 数独の外枠を表現します。<br>
 * ・{@code size=2}の場合、サブパネルを4枚保持します。<br>
 * ・{@code size=3}の場合、サブパネルを9枚保持します。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
public class SudokuMainFrame implements Serializable {

    private static final long serialVersionUID = 1L;
    // (row,col)の座標のリスト
    private List<Pair<Integer, Integer>> coordinates;
    private Map<Pair<Integer, Integer>, SudokuSubPanel> subPanelMap;
    // パネルの配置数
    private int size;
    private boolean confirmFlg;

    public SudokuMainFrame(int size) {
        coordinates = new ArrayList<>();
        subPanelMap = new HashMap<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Pair<Integer, Integer> coordinate = getCoordinate(row, col);
                coordinates.add(coordinate);
                subPanelMap.put(coordinate, new SudokuSubPanel(size));
            }
        }
        this.size = size;
        confirmFlg = false;
    }

    public Pair<Integer, Integer> getCoordinate(int row, int col) {
        return Pair.of(row, col);
    }

    public SudokuSubPanel findSubPanel(int row, int col) {
        return findSubPanel(Pair.of(row, col));
    }

    private SudokuSubPanel findSubPanel(Pair<Integer, Integer> coordinate) {
        return subPanelMap.get(coordinate);
    }

    @Deprecated
    public List<SudokuSubPanel> findSubPanelByRow(int size, int row) {
        List<SudokuSubPanel> result = new ArrayList<>();
        for (int col = 0; col < size; col++) {
            Pair<Integer, Integer> coordinate = getCoordinate(row, col);
            result.add(subPanelMap.get(coordinate));
        }
        return result;
    }

    @Deprecated
    public List<SudokuSubPanel> findSubPanelByCol(int size, int col) {
        List<SudokuSubPanel> result = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            Pair<Integer, Integer> coordinate = getCoordinate(row, col);
            result.add(subPanelMap.get(coordinate));
        }
        return result;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        subPanelMap.forEach((key, value) -> sj.add(key + ":" + value));
        return sj.toString();
    }

}
