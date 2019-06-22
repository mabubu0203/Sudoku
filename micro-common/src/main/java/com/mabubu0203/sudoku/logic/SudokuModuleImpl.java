package com.mabubu0203.sudoku.logic;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.enums.Difficulty;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.utils.SudokuUtils;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.iterator.IntIterator;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.ListIterator;

@Component
@Slf4j
public class SudokuModuleImpl implements SudokuModule {

    @Override
    public int getWarmEatenValue(String level, int size) {
        String key = level.toUpperCase().concat(Integer.toString(size));
        Difficulty difficulty = Difficulty.getDifficulty(key);
        if (difficulty == null) {
            return 0;
        } else {
            return difficulty.getValue();
        }
    }

    @Override
    public void filteredCell(NumberPlaceBean numberPlaceBean, String key) throws SudokuApplicationException {
        try {
            PropertyDescriptor properties = new PropertyDescriptor(key, numberPlaceBean.getClass());
            Method setter = properties.getWriteMethod();
            if (setter != null) {
                setter.invoke(numberPlaceBean, CommonConstants.INTEGER_ZERO);
            }
        } catch (IntrospectionException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }
    }

    @Override
    public void createFirstRow(int[][] numberPlace, int size) {
        ImmutableIntList intList = ESListWrapUtils.getRandList(size);
        intList.forEachWithIndex((s, i) -> numberPlace[0][i] = s);
    }

    @Override
    public void createLastRow(int[][] numberPlace, int size) {
        for (int x = 0; x < size; x++) {
            numberPlace[size - 1][x] = lastColumnCell(numberPlace, x, size);
        }
    }

    @Override
    public int lastColumnCell(int[][] numberPlace, int x, int size) {
        int cell = 0;
        for (int i = 0; i < size + 1; i++) {
            cell += i;
            if (i < size - 1) {
                cell -= numberPlace[i][x];
            }
        }
        return cell;
    }

    @Override
    public void createMiddleRows(int[][] numberPlace, int y, int size) {
        boolean isCheck = false;
        while (!isCheck) {
            isCheck = true;
            IntIterator intIterator = ESListWrapUtils.getRandList(size).intIterator();
            for (int x = 0; x < size; x++) {
                int column = intIterator.next();
                if (isCheck(numberPlace, x, y, column, size)) {
                    numberPlace[y][x] = column;
                    continue;
                } else {
                    isCheck = false;
                    break;
                }
            }
        }
    }

    @Override
    public boolean isCheck(int[][] numberPlace, int x, int y, int column, int size) {
        // y軸判定
        int[] copyY = new int[y];
        for (int k = 0; k < y; k++) {
            copyY[k] = numberPlace[k][x];
        }
        if (!isNotEqualNumber(numberPlace, y, column, copyY)) {
            return false;
        }
        // 正方形の縦・横のサイズを平方根より求めます。
        int squareRoot = SudokuUtils.convertSquareRoot(size);

        // 区画判定
        if (y % squareRoot == 0) {
            return true;
        } else {
            int boxY = (y / squareRoot) * squareRoot;
            int boxX = (x / squareRoot) * squareRoot;
            int[] copyBlock = new int[size];
            int arrayNum = 0;
            for (int k = boxY; k < boxY + squareRoot; k++) {
                for (int l = boxX; l < boxX + squareRoot; l++) {
                    copyBlock[arrayNum++] = numberPlace[k][l];
                }
            }
            return isNotEqualNumber(numberPlace, y, column, copyBlock);
        }
    }

    @Override
    public boolean isNotEqualNumber(int[][] numberPlace, int y, int column, int[] copyArray) {
        if (Arrays.stream(copyArray).anyMatch(s -> s == column)) {
            Arrays.fill(numberPlace[y], 0);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String createAnswerKey(int[][] numberPlace) {
        StringBuilder answerKey = new StringBuilder();
        for (int[] arrays : numberPlace) {
            for (int array : arrays) {
                answerKey.append(array);
            }
        }
        return answerKey.toString();
    }

    @Override
    public String convertToSha256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes(CommonConstants.UTF8));
            byte[] cipherByte = md.digest();
            StringBuilder hash = new StringBuilder(2 * cipherByte.length);
            for (byte b : cipherByte) {
                hash.append(String.format("%02x", b & 0xff));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

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
                createFirstRow(moduleBean.getNumberPlaceArray(), moduleBean.getSize());
            } else if (y > 0 && y < moduleBean.getSize() - 1) {
                // ２行目〜
                createMiddleRows(moduleBean.getNumberPlaceArray(), y, moduleBean.getSize());
            } else if (y == moduleBean.getSize() - 1) {
                // 最後
                createLastRow(moduleBean.getNumberPlaceArray(), moduleBean.getSize());
            }
        }
    }

    private void arrayConvertBean(SudokuModuleBean moduleBean) {
        String answerKey = createAnswerKey(moduleBean.getNumberPlaceArray());
        String keyHash = convertToSha256(answerKey);
        if (keyHash == null) {
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
                SudokuUtils.setCell(moduleBean.getNumberPlaceBean(), itr.next(), moduleBean.getNumberPlaceArray()[y][x++]);
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
        int type = moduleBean.getSize();
        ListIterator<String> itr =
                ESListWrapUtils.createCells(moduleBean.getSize(), getWarmEatenValue(level, moduleBean.getSize())).listIterator();
        try {
            while (itr.hasNext()) {
                filteredCell(moduleBean.getNumberPlaceBean(), itr.next());
            }
        } catch (SudokuApplicationException e) {
            e.printStackTrace();
            log.error("やらかしています。");
            moduleBean.setNumberPlaceBean(new NumberPlaceBean());
        }
    }

}
