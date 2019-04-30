package com.mabubu0203.sudoku.web.utils;

import com.mabubu0203.sudoku.constants.CommonConstants;
import com.mabubu0203.sudoku.exception.SudokuApplicationException;
import com.mabubu0203.sudoku.interfaces.NumberPlaceBean;
import com.mabubu0203.sudoku.utils.ESListWrapUtils;
import com.mabubu0203.sudoku.web.form.PlayForm;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ListIterator;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

/**
 * <br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class CompareUtil {

    /**
     * <br>
     *
     * @param form
     * @param bean
     * @author uratamanabu
     * @since 1.0
     */
    public static void playFormCompareAnswer(PlayForm form, NumberPlaceBean bean) {

        if (!Objects.equals(bean.getType(), form.getType())) {
            throw new SudokuApplicationException();
        } else if (!bean.getKeyHash().equals(form.getKeyHash())) {
            throw new SudokuApplicationException();
        }
        form.setCompareFlg(true);
        form.addCount();
        ListIterator<String> itr = ESListWrapUtils.createCells(bean.getType(), 0).listIterator();
        try {
            while (itr.hasNext()) {
                String property = itr.next();
                PropertyDescriptor formProperties = new PropertyDescriptor(property, form.getClass());
                PropertyDescriptor beanProperties = new PropertyDescriptor(property, bean.getClass());
                Method formGetter = formProperties.getReadMethod();
                Method beanGetter = beanProperties.getReadMethod();
                if (Objects.isNull(formGetter) || Objects.isNull(beanGetter)) {
                    throw new SudokuApplicationException();
                }
                Integer formValue = (Integer) formGetter.invoke(form, (Object[]) null);
                Integer beanValue = (Integer) beanGetter.invoke(bean, (Object[]) null);
                if (Objects.isNull(formValue) || Objects.isNull(beanValue)) {
                    throw new SudokuApplicationException();
                } else if (!Objects.equals(formValue, beanValue)) {
                    Method formSetter = formProperties.getWriteMethod();
                    if (Objects.isNull(formSetter)) {
                        throw new SudokuApplicationException();
                    } else {
                        formSetter.invoke(form, CommonConstants.INTEGER_ZERO);
                        form.subtractionScore(50);
                        form.setCompareFlg(false);
                    }
                }
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
