package com.mabubu0203.sudoku.utils;

import com.mabubu0203.sudoku.enums.Difficulty;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
public class SudokuUtils {

    /**
     * 引数で与えられた整数の平方根を返します。<br>
     * .
     *
     * @param num
     * @return 平方根
     */
    public static int convertSquareRoot(int num) {
        return (int) Math.sqrt(num);
    }

    /**
     * .
     *
     * @param selectType
     * @param selectLevel
     * @return
     * @throws SudokuApplicationException
     */
    public static int calculationScore(int selectType, String selectLevel)
            throws SudokuApplicationException {
        String key = selectLevel.toUpperCase().concat(Integer.toString(selectType));
        Difficulty difficulty = Difficulty.getDifficulty(key);
        if (difficulty == null) {
            throw new SudokuApplicationException();
        }
        switch (difficulty) {
            case EASY4:
                return 2000;
            case EASY9:
                return 2000;
            case NORMAL4:
                return 5000;
            case NORMAL9:
                return 5000;
            case HARD4:
                return 10000;
            case HARD9:
                return 15000;
            default:
                throw new SudokuApplicationException();
        }
    }

    /**
     * .
     *
     * @param numberPlaceBean
     * @param key
     * @param value
     * @throws SudokuApplicationException
     */
    public static void setCell(NumberPlaceBean numberPlaceBean, String key, int value)
            throws SudokuApplicationException {
        try {
            PropertyDescriptor properties = new PropertyDescriptor(key, numberPlaceBean.getClass());
            Method setter = properties.getWriteMethod();
            if (setter != null) {
                setter.invoke(numberPlaceBean, value);
            }
        } catch (IntrospectionException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
            throw new SudokuApplicationException();
        }
    }
}
