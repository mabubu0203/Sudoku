package com.mabubu0203.sudoku.components.impl;

import com.mabubu0203.sudoku.components.SudokuGenerator;
import com.mabubu0203.sudoku.interfaces.beans.SudokuBean;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import lombok.AllArgsConstructor;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
public class WIPSudokuGeneratorImpl implements SudokuGenerator {

    @Override
    public void generate(int type) {
        SudokuBean sudokuBean = generateSudokuBean(type);

        //SudokuMainFrame mainFrame = generateNumberPlace(type);
        //NumberPlaceBean numberPlaceBean = mainFrameConvertNumberPlaceBean(mainFrame);
        return;
    }

    private SudokuBean generateSudokuBean(int type) {
        int size = SudokuUtils.convertSquareRoot(type);
        SudokuBean sudokuBean = new SudokuBean(size, type);

        for (int row = 0; row < size; row++) {

            if (row == 0) {
                // 最初
                createFirstRow(sudokuBean);
            } else if (row == size - 1) {
                // 最後
                //createLastRow(sudokuBean);
            } else {
                // 上記以外
                //createMiddleRows(sudokuBean, row);
            }

        }
        return sudokuBean;

    }

    private void createFirstRow(SudokuBean sudokuBean) {

        SudokuBean.SubPanel firstPanel = sudokuBean.getSubPanel();
        Map<Pair<Integer, Integer>, SudokuBean.Cell> cellMap = firstPanel.getCellMap();
        // 座標を初期化
        List<Pair<Integer, Integer>> coordinates = firstPanel.getCellMapCoordinates();
        initCoordinates(sudokuBean.getSize(), firstPanel.getCellMapCoordinates());

        ImmutableIntList intList = ESListWrapUtils.getRandList(sudokuBean.getType());
        intList.forEachWithIndex((s, i) -> cellMap.put(coordinates.get(i), sudokuBean.getSubCell(s)));

        firstPanel.setCellMap(cellMap);

        SudokuBean.MainFrame mainFrame = sudokuBean.getMainFrame();
        mainFrame.getSubPanelMap().put(Pair.of(0, 0), firstPanel);
    }

    private void initCoordinates(int size, List<Pair<Integer, Integer>> coordinates) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Pair<Integer, Integer> coordinate = Pair.of(row, col);
                coordinates.add(coordinate);
            }
        }

    }

}
