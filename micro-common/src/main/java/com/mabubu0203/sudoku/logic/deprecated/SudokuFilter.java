package com.mabubu0203.sudoku.logic.deprecated;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.enums.Difficulty;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Deprecated
public interface SudokuFilter {

    /**
     * @param level
     * @param size
     * @return
     */
    default int getWarmEatenValue(String level, int size) {
        String key = level.toUpperCase().concat(Integer.toString(size));
        Difficulty difficulty = Difficulty.getDifficulty(key);
        if (difficulty == null) {
            return 0;
        } else {
            return difficulty.getValue();
        }
    }

    /**
     * @param numberPlaceBean
     * @param key
     * @throws SudokuApplicationException
     */
    default void filteredCell(NumberPlaceBean numberPlaceBean, String key)
            throws SudokuApplicationException {
        try {
            PropertyDescriptor properties = new PropertyDescriptor(key, numberPlaceBean.getClass());
            Method setter = properties.getWriteMethod();
            if (setter != null) {
                setter.invoke(numberPlaceBean, CommonConstants.ZERO);
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
