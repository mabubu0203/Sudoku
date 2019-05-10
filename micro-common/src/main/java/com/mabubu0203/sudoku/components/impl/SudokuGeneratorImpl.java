package com.mabubu0203.sudoku.components.impl;

import com.mabubu0203.sudoku.components.SudokuGenerator;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.beans.*;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.ESSetWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import lombok.AllArgsConstructor;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 解答を生成する為のコンポーネントクラスです。<br>
 * このクラスを経由してロジックを実行してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
@Component
@Deprecated
public class SudokuGeneratorImpl implements SudokuGenerator {

    @Override
    public void generate(int type) {
        SudokuMainFrame mainFrame = generateNumberPlace(type);
        NumberPlaceBean numberPlaceBean = mainFrameConvertNumberPlaceBean(mainFrame);
        return;
    }

    @Deprecated
    private SudokuMainFrame generateNumberPlace(int type) {
        int size = SudokuUtils.convertSquareRoot(type);
        SudokuMainFrame mainFrame = new SudokuMainFrame(size);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 0 && col == 0) {
                    // 最初
                    createFirstPanel(mainFrame, type);
                } else if (row == size - 1 && col == size - 1) {
                    // 最後
                    createLastPanel(mainFrame);
                } else {
                    // 上記以外
                    createMiddlePanels(mainFrame, row, col);
                }
            }
        }
        return mainFrame;
    }

    private NumberPlaceBean mainFrameConvertNumberPlaceBean(SudokuMainFrame mainFrame) {
        return new NumberPlaceBean();
    }

    private void createFirstPanel(SudokuMainFrame mainFrame, int type) {
        SudokuSubPanel firstPanel = mainFrame.findSubPanel(0, 0);
        Map<Pair<Integer, Integer>, SudokuCell> cellMap = firstPanel.getCellMap();
        ImmutableIntList intList = ESListWrapUtils.getRandList(type);
        List<Pair<Integer, Integer>> coordinates = firstPanel.getCoordinates();
        intList.forEachWithIndex((s, i) -> cellMap.put(coordinates.get(i), new SudokuCell(s)));
        firstPanel.setCellMap(cellMap);
    }

    private void createMiddlePanels(SudokuMainFrame mainFrame, int row, int col) {
        return;
    }

    private void createLastPanel(SudokuMainFrame mainFrame) {

        Map<Pair<Integer, Integer>, SudokuSubPanel> subPanelMap = mainFrame.getSubPanelMap();
        int size = mainFrame.getSize();

        List<SudokuSubPanel> lastRowPanels = new ArrayList<>();
        List<SudokuSubPanel> lastColPanels = new ArrayList<>();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (col == size - 1 && row == size - 1) {
                    break;
                } else if (col == size - 1) {
                    lastColPanels.add(subPanelMap.get(Pair.of(row, col)));
                } else if (row == size - 1) {
                    lastRowPanels.add(subPanelMap.get(Pair.of(row, col)));
                }
            }
        }

        ExpectSubPanel expectPanel = new ExpectSubPanel(size);
        expectPanel.setExpectRowList(kaisekiRow(size, lastRowPanels));
        expectPanel.setExpectColList(kaisekiCol(size, lastColPanels));
        calculateLastPanel(size, expectPanel);
        SudokuSubPanel lastPanel = expectConvertPanel(size, expectPanel);

    }

    @Deprecated
    private List<Set<Integer>> kaisekiRow(int size, List<SudokuSubPanel> lastRowPanels) {
        List<Set<Integer>> expectRowList = new ArrayList<>();
        for (int col = 0; col < size; col++) {
            Set<Integer> set = new HashSet<>();
            Set<Integer> linkedNum = ESSetWrapUtils.getLinkedNum(size * size);
            for (SudokuSubPanel panel : lastRowPanels) {
                List<SudokuCell> cells = panel.findCellByCol(size, col);
                for (SudokuCell cell : cells) {
                    int value = cell.getValue();
                    linkedNum.remove(value);
                }
            }
            set.addAll(linkedNum);
            expectRowList.add(set);
        }
        return expectRowList;
    }

    @Deprecated
    private List<Set<Integer>> kaisekiCol(int size, List<SudokuSubPanel> lastColPanels) {
        List<Set<Integer>> expectColList = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            Set<Integer> set = new HashSet<>();
            Set<Integer> linkedNum = ESSetWrapUtils.getLinkedNum(size * size);
            for (SudokuSubPanel panel : lastColPanels) {
                List<SudokuCell> cells = panel.findCellByRow(size, row);
                for (SudokuCell cell : cells) {
                    int value = cell.getValue();
                    linkedNum.remove(value);
                }
            }
            set.addAll(linkedNum);
            expectColList.add(set);
        }
        return expectColList;
    }

    private void calculateLastPanel(int size, ExpectSubPanel expectPanel) {
        do {
            Map<Pair<Integer, Integer>, ExpectCell> cellMap = expectPanel.getCellMap();
            for (int row = 0; row < size; row++) {
                Set<Integer> expectRow = expectPanel.getExpectRowList().get(row);
                for (int col = 0; col < size; col++) {
                    Set<Integer> expectCol = expectPanel.getExpectColList().get(col);
                    for (Integer expectValue : expectCol) {
                        if (expectRow.contains(expectValue)) {
                            cellMap.get(Pair.of(row, col)).addExpectValues(expectValue);
                        }
                    }

                }
            }
            cellMap.forEach((key, value) -> {

                Set<Integer> expectValues = value.getExpectValues();
                // 期待値が1種類しかない場合
                if (expectValues.size() == 1) {
                    int cellValue = expectValues.iterator().next();
                    value.setValue(cellValue);
                    expectValues.remove(cellValue);

                    // ExpectRowListの該当行から期待値を削除する。
                    expectPanel.getExpectRowList().get(key.getFirst()).remove(cellValue);
                    // ExpectColListの該当列から期待値を削除する。
                    expectPanel.getExpectColList().get(key.getSecond()).remove(cellValue);
                }
            });
            expectPanel.setCellMap(cellMap);
            if (expectPanel.getExpectRowList().stream().distinct().count() == 0
                    && expectPanel.getExpectColList().stream().distinct().count() == 0) {
                expectPanel.setConfirmFlg(true);
            }
        } while (expectPanel.isConfirmFlg());

    }

    private SudokuSubPanel expectConvertPanel(int size, ExpectSubPanel expectPanel) {
        SudokuSubPanel lastPanel = new SudokuSubPanel(size);

        Map<Pair<Integer, Integer>, SudokuCell> result = new HashMap<>();
        Map<Pair<Integer, Integer>, ExpectCell> cellMap = expectPanel.getCellMap();
        cellMap.forEach((key, value) -> result.put(key, new SudokuCell(value.getValue())));

        lastPanel.setCellMap(result);
        return lastPanel;
    }

}

