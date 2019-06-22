package com.mabubu0203.sudoku.logic;

import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SudokuModule {

    /**
     * 虫食い数を取得する。
     *
     * @param level
     * @param size
     * @return
     */
    int getWarmEatenValue(String level, int size);

    /**
     * 虫食いを実行する。
     *
     * @param numberPlaceBean
     * @param key
     * @throws SudokuApplicationException
     */
    void filteredCell(NumberPlaceBean numberPlaceBean, String key) throws SudokuApplicationException;

    /**
     * 数独の１行目を作成します。
     *
     * @param numberPlace 　数独の配列
     * @param size
     */
    void createFirstRow(int[][] numberPlace, int size);

    /**
     * 数独の最後の行を作成します。
     *
     * @param numberPlace 　数独の配列
     * @param size
     */
    void createLastRow(int[][] numberPlace, int size);

    /**
     * 最後の列の数値を算出します。
     *
     * @param numberPlace 　数独の配列
     * @param x
     * @param size
     * @return 列から逆算した値
     */
    int lastColumnCell(int[][] numberPlace, int x, int size);

    /**
     * 数独のy列を作成します。
     *
     * @param numberPlace 　数独の配列
     * @param y
     * @param size
     */
    void createMiddleRows(int[][] numberPlace, int y, int size);

    /**
     * @param numberPlace 　数独の配列
     * @param x
     * @param y
     * @param column
     * @param size
     * @return
     */
    boolean isCheck(int[][] numberPlace, int x, int y, int column, int size);

    /**
     * @param numberPlace 　数独の配列
     * @param y
     * @param column
     * @param copyArray
     * @return
     */
    boolean isNotEqualNumber(int[][] numberPlace, int y, int column, int[] copyArray);

    /**
     * AnswerKeyを作成する。
     *
     * @param numberPlace
     * @return
     */
    @Deprecated
    String createAnswerKey(int[][] numberPlace);

    /**
     * SHA256変換した文字列を返却します。
     *
     * @param str 文字列
     * @return SHA256変換した文字列かnull
     */
    @Deprecated
    String convertToSha256(String str);

    /**
     * 数独を0から生成し返却します。
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    NumberPlaceBean generate(int type);

    /**
     * 数独をマスキングして返却します。
     *
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    NumberPlaceBean filteredOfLevel(int type, NumberPlaceBean bean, String level);
}
