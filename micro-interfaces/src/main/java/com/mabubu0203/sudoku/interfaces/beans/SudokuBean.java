package com.mabubu0203.sudoku.interfaces.beans;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.*;

@Data
public class SudokuBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // SubPanelの配置数(typeの平方根)
    private int size;

    // 行,列の配置数
    private int type;

    private MainFrame mainFrame;

    public SudokuBean(int size, int type) {
        this.size = size;
        this.type = type;
        this.mainFrame = new MainFrame();
    }

    public SubPanel getSubPanel() {
        return new SubPanel();
    }

    public SubCell getSubCell(final int value) {
        return new SubCell(value);
    }

    @Override
    public String toString() {
        return mainFrame.toString();
    }

    /**
     * 数独の外枠を表現します。<br>
     * ・{@code size=2}の場合、サブパネルを4枚保持します。セルは16枚保持します。<br>
     * ・{@code size=3}の場合、サブパネルを9枚保持します。セルは91枚保持します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Data
    public class MainFrame implements Serializable {

        private static final long serialVersionUID = 1L;

        // 座標(row,col) のList(SubPanel基準)
        private List<Pair<Integer, Integer>> subPanelCoordinates;

        // 座標(row,col) のList(SubCell基準)
        private List<Pair<Integer, Integer>> subCellMapCoordinates;

        // key:value = 座標:SubPanel のmap
        private Map<Pair<Integer, Integer>, SubPanel> subPanelMap;

        // key:value = 座標:SubCell のmap
        private Map<Pair<Integer, Integer>, SubCell> subCellMap;


        public MainFrame() {
            this.subPanelCoordinates = new ArrayList<>();
            this.subCellMapCoordinates = new ArrayList<>();
            this.subPanelMap = new HashMap<>();
            this.subCellMap = new HashMap<>();
        }

        public SubPanel findSubPanel(int row, int col) {
            return findSubPanel(Pair.of(row, col));
        }

        private SubPanel findSubPanel(Pair<Integer, Integer> coordinate) {
            return this.subPanelMap.get(coordinate);
        }

        private SubCell findSubCell(Pair<Integer, Integer> coordinate) {
            return this.subCellMap.get(coordinate);
        }

        public List<Integer> getRowList(int row) {
            List<Integer> result = new ArrayList<>();
            this.subCellMap.forEach((key, value) -> {
                if (key.getFirst().equals(row)) {
                    result.add(value.getValue());
                }
            });
            return result;
        }

        public List<Integer> getColList(int col) {
            List<Integer> result = new ArrayList<>();
            this.subCellMap.forEach((key, value) -> {
                if (key.getSecond().equals(col)) {
                    result.add(value.getValue());
                }
            });
            return result;
        }

        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(",");
            this.subPanelMap.forEach((key, value) -> sj.add(key + ":" + value));
            return sj.toString();
        }

    }

    /**
     * 数独のパネルを表現します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Data
    public class SubPanel extends Panel {

        private static final long serialVersionUID = 1L;

        public SubPanel() {
            super();
        }

    }

    /**
     * 数独のセルを表現します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Data
    public class SubCell extends Cell {

        private static final long serialVersionUID = 1L;

        public SubCell(final int value) {
            super(value);
        }

    }


    /**
     * 数独の予想パネルを表現します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Data
    public class ExpectSubPanel extends Panel {

        private static final long serialVersionUID = 1L;

        private List<Set<Integer>> expectRowList;
        private List<Set<Integer>> expectColList;

        public ExpectSubPanel() {
            super();
            this.expectRowList = new ArrayList<>();
            this.expectColList = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(",");
            sj.add(super.toString());
            sj.add(this.expectRowList.toString());
            sj.add(this.expectColList.toString());
            return sj.toString();
        }

    }

    /**
     * 数独の予想セルを表現します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Data
    public class ExpectCell extends Cell {

        private static final long serialVersionUID = 1L;

        private Set<Integer> expectValues;

        public ExpectCell(int value) {
            super(value);
            this.expectValues = new HashSet<>();
        }

        public void addExpectValues(int value) {
            this.expectValues.add(value);
        }

        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(",");
            sj.add(super.toString());
            sj.add(this.expectValues.toString());
            return sj.toString();
        }

    }

    /**
     * 数独のパネルを表現します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Data
    public class Panel implements Serializable {

        private static final long serialVersionUID = 1L;

        // 座標(row,col) のList(SubCell基準)
        private List<Pair<Integer, Integer>> cellMapCoordinates;

        // key:value = 座標:SubCell のmap
        private Map<Pair<Integer, Integer>, Cell> cellMap;

        public Panel() {
            this.cellMapCoordinates = new ArrayList<>();
            this.cellMap = new HashMap<>();
        }

        public Cell findCell(int row, int col) {
            return findCell(Pair.of(row, col));
        }

        private Cell findCell(Pair<Integer, Integer> coordinate) {
            return this.cellMap.get(coordinate);
        }

        @Override
        public String toString() {
            StringJoiner sj = new StringJoiner(",");
            this.cellMap.forEach((key, value) -> sj.add(key + ":" + value));
            return sj.toString();
        }
    }

    /**
     * 数独のセルを表現します。<br>
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Data
    public class Cell implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer value;

        public Cell(final int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

}
