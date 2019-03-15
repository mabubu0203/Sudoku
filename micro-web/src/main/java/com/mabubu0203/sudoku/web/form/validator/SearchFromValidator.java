package com.mabubu0203.sudoku.web.form.validator;

import com.mabubu0203.sudoku.validator.BaseFormValidator;
import com.mabubu0203.sudoku.web.form.SearchForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Component
public class SearchFromValidator extends BaseFormValidator {

    /**
     * @param paramClass
     * @author uratamanabu
     * @since 1.0
     */
    @Override
    public boolean supports(Class<?> paramClass) {
        return SearchForm.class.isAssignableFrom(paramClass);
    }

    /**
     * @param paramObject
     * @param errors
     * @author uratamanabu
     * @since 1.0
     */
    @Override
    public void validate(Object paramObject, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        SearchForm form = (SearchForm) paramObject;
        Long no = form.getNo();
        int selectType = form.getSelectType();
        int score = form.getScore();
        String keyHash = form.getKeyHash();
        String name = form.getName();
    }
}
