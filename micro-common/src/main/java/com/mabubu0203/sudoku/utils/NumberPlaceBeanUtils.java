package com.mabubu0203.sudoku.utils;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

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
public class NumberPlaceBeanUtils {

    /**
     * 引数で与えられた{@code numberPlaceBean}の{@code key}に{@code value}を設定します。<br>
     *
     * @param numberPlaceBean
     * @param key
     * @param value
     */
    public static void setCell(NumberPlaceBean numberPlaceBean, String key, int value) {
        try {
            PropertyDescriptor properties = new PropertyDescriptor(key, numberPlaceBean.getClass());
            Method setter = properties.getWriteMethod();
            if (Objects.nonNull(setter)) {
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

    /**
     * 虫食いを実行する。<br>
     *
     * @param numberPlaceBean
     * @param key
     * @throws SudokuApplicationException
     */
    public static void filteredCell(NumberPlaceBean numberPlaceBean, String key) throws SudokuApplicationException {
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
}
