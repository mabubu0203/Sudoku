package com.mabubu0203.sudoku.logic.impl;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.logic.SudokuModule;
import com.mabubu0203.sudoku.logic.SudokuModuleBean;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.NumberPlaceArrayUtils;
import com.mabubu0203.sudoku.utils.NumberPlaceBeanUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ListIterator;

@Component
@Slf4j
public class SudokuModuleImpl implements SudokuModule {

    @Override
    public NumberPlaceBean generate(int type) {
        SudokuModuleBean moduleBean = new SudokuModuleBean(type);
        createAnswerArray(moduleBean);
        arrayConvertBean(moduleBean);
        return moduleBean.getNumberPlaceBean();
    }

    @Override
    public NumberPlaceBean filteredOfLevel(int type, NumberPlaceBean bean, String level) {
        SudokuModuleBean moduleBean = new SudokuModuleBean(type);
        moduleBean.setNumberPlaceBean(bean);
        filteredOfLevel(moduleBean, level);
        return moduleBean.getNumberPlaceBean();
    }

    private void createAnswerArray(SudokuModuleBean moduleBean) {
        for (int y = 0; y < moduleBean.getSize(); y++) {
            // 1行毎配列の要素を組み立てる
            // 行によって処理を分岐する
            if (y == 0) {
                // １行目
                NumberPlaceArrayUtils.createFirstRow(moduleBean.getNumberPlaceArray(), moduleBean.getSize());
            } else if (y > 0 && y < moduleBean.getSize() - 1) {
                // ２行目〜
                NumberPlaceArrayUtils.createMiddleRows(moduleBean.getNumberPlaceArray(), y, moduleBean.getSize());
            } else if (y == moduleBean.getSize() - 1) {
                // 最後
                NumberPlaceArrayUtils.createLastRow(moduleBean.getNumberPlaceArray(), moduleBean.getSize());
            }
        }
    }

    private void arrayConvertBean(SudokuModuleBean moduleBean) {
        String answerKey = NumberPlaceArrayUtils.createAnswerKey(moduleBean.getNumberPlaceArray());
        String keyHash = SudokuUtils.convertToSha256(answerKey);
        if (StringUtils.isEmpty(keyHash)) {
            moduleBean.setNumberPlaceBean(new NumberPlaceBean());
        } else {
            moduleBean.getNumberPlaceBean().setType(moduleBean.getSize());
            moduleBean.getNumberPlaceBean().setNo(CommonConstants.LONG_ZERO);
            moduleBean.getNumberPlaceBean().setAnswerKey(answerKey);
            moduleBean.getNumberPlaceBean().setKeyHash(keyHash);
            setCells(moduleBean);
        }
    }

    private void setCells(SudokuModuleBean moduleBean) {

        ListIterator<String> itr = ESListWrapUtils.createCells(moduleBean.getSize(), 0).listIterator();
        int x = 0;
        int y = 0;

        try {
            // TODO:移植中
            while (itr.hasNext()) {
                NumberPlaceBeanUtils.setCell(
                        moduleBean.getNumberPlaceBean(),
                        itr.next(),
                        moduleBean.getNumberPlaceArray()[y][x++]
                );
                if (x == moduleBean.getSize()) {
                    x = 0;
                    y++;
                }
            }
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            log.error("やらかしています。");
            moduleBean.setNumberPlaceBean(new NumberPlaceBean());
        }
    }

    private void filteredOfLevel(SudokuModuleBean moduleBean, String level) {
        ListIterator<String> itr =
                ESListWrapUtils.createCells(
                        moduleBean.getSize(),
                        SudokuUtils.getWarmEatenValue(moduleBean.getSize(), level)
                ).listIterator();
        try {
            while (itr.hasNext()) {
                NumberPlaceBeanUtils.filteredCell(moduleBean.getNumberPlaceBean(), itr.next());
            }
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            log.error("やらかしています。");
            moduleBean.setNumberPlaceBean(new NumberPlaceBean());
        }
    }

}
