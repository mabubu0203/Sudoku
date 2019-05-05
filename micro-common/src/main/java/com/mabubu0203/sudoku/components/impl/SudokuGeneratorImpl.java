package com.mabubu0203.sudoku.components.impl;

import com.mabubu0203.sudoku.components.SudokuGenerator;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.interfaces.beans.SudokuCell;
import com.mabubu0203.sudoku.interfaces.beans.SudokuMainFrame;
import com.mabubu0203.sudoku.interfaces.beans.SudokuSubPanel;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.ESSetWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import lombok.AllArgsConstructor;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class SudokuGeneratorImpl implements SudokuGenerator {

    @Override
    public void generate(int type) {
        SudokuMainFrame mainFrame = generateNumberPlace(type);
        NumberPlaceBean numberPlaceBean = mainFrameConvertNumberPlaceBean(mainFrame);
        return;
    }

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

        Pair<Integer, Integer> lastPanelCoordinate = Pair.of(size - 1, size - 1);
        SudokuSubPanel lastPanel = subPanelMap.get(lastPanelCoordinate);

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
        kaisekiRow(size, lastRowPanels);
        kaisekiCol(size, lastColPanels);

    }

    @Deprecated
    private void kaisekiRow(int size, List<SudokuSubPanel> lastRowPanels) {
        List<List<Integer>> lastRowList = new ArrayList<>();
        for (int col = 0; col < size; col++) {
            List<Integer> list = new ArrayList<>();
            Set<Integer> linkedNum = ESSetWrapUtils.getLinkedNum(size * size);
            for (SudokuSubPanel panel : lastRowPanels) {
                List<SudokuCell> cells = panel.findCellByCol(size, col);
                for (SudokuCell cell : cells) {
                    int value = cell.getValue();
                    linkedNum.remove(value);

                }
            }
            list.addAll(linkedNum);
            lastRowList.add(list);
        }
    }

    @Deprecated
    private void kaisekiCol(int size, List<SudokuSubPanel> lastColPanels) {
        List<List<Integer>> lastColList = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            List<Integer> list = new ArrayList<>();
            Set<Integer> linkedNum = ESSetWrapUtils.getLinkedNum(size * size);
            for (SudokuSubPanel panel : lastColPanels) {
                List<SudokuCell> cells = panel.findCellByRow(size, row);
                for (SudokuCell cell : cells) {
                    int value = cell.getValue();
                    linkedNum.remove(value);

                }
            }
            list.addAll(linkedNum);
            lastColList.add(list);
        }
    }

}

