package com.mabubu0203.sudoku.web.form.validator;

import com.mabubu0203.sudoku.validator.BaseFormValidator;
import com.mabubu0203.sudoku.web.form.ScoreForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Component
public class ScoreFormValidator extends BaseFormValidator {

    /**
     * @param paramClass
     * @author uratamanabu
     * @since 1.0
     */
    @Override
    public boolean supports(Class<?> paramClass) {
        return ScoreForm.class.isAssignableFrom(paramClass);
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
        ScoreForm form = (ScoreForm) paramObject;
        int score = form.getScore();
        String keyHash = form.getKeyHash();
        String name = form.getName();
    }
}
