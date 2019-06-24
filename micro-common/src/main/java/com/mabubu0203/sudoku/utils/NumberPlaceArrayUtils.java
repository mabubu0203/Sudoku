package com.mabubu0203.sudoku.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.iterator.IntIterator;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

import java.util.Arrays;

import static lombok.AccessLevel.PRIVATE;

/**
 * 数独に使用するユーティル群をまとめたクラスです。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class NumberPlaceArrayUtils {

    /**
     * 数独の１行目を作成します。
     *
     * @param numberPlace 　数独の配列
     * @param size
     */
    public static void createFirstRow(int[][] numberPlace, int size) {
        ImmutableIntList intList = ESListWrapUtils.getRandList(size);
        intList.forEachWithIndex((s, i) -> numberPlace[0][i] = s);
    }

    /**
     * 数独の最後の行を作成します。
     *
     * @param numberPlace 　数独の配列
     * @param size
     */
    public static void createLastRow(int[][] numberPlace, int size) {
        for (int x = 0; x < size; x++) {
            numberPlace[size - 1][x] = lastColumnCell(numberPlace, x, size);
        }
    }

    /**
     * 最後の列の数値を算出します。
     *
     * @param numberPlace 　数独の配列
     * @param x
     * @param size
     * @return 列から逆算した値
     */
    public static int lastColumnCell(int[][] numberPlace, int x, int size) {
        int cell = 0;
        for (int i = 0; i < size + 1; i++) {
            cell += i;
            if (i < size - 1) {
                cell -= numberPlace[i][x];
            }
        }
        return cell;
    }

    /**
     * 数独のy列を作成します。
     *
     * @param numberPlace 　数独の配列
     * @param y
     * @param size
     */
    public static void createMiddleRows(int[][] numberPlace, int y, int size) {
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

    /**
     * @param numberPlace 　数独の配列
     * @param x
     * @param y
     * @param column
     * @param size
     * @return
     */
    public static boolean isCheck(int[][] numberPlace, int x, int y, int column, int size) {
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

    /**
     * @param numberPlace 　数独の配列
     * @param y
     * @param column
     * @param copyArray
     * @return
     */
    public static boolean isNotEqualNumber(int[][] numberPlace, int y, int column, int[] copyArray) {
        if (Arrays.stream(copyArray).anyMatch(s -> s == column)) {
            Arrays.fill(numberPlace[y], 0);
            return false;
        } else {
            return true;
        }
    }

    /**
     * AnswerKeyを作成し、返却します。<br>
     *
     * @param numberPlace
     * @return AnswerKey
     */
    public static String createAnswerKey(int[][] numberPlace) {
        StringBuilder answerKey = new StringBuilder();
        for (int[] arrays : numberPlace) {
            for (int array : arrays) {
                answerKey.append(array);
            }
        }
        return answerKey.toString();
    }

}
