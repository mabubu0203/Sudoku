package com.mabubu0203.sudoku.web.form.validator;

import com.mabubu0203.sudoku.validator.BaseFormValidator;
import com.mabubu0203.sudoku.web.form.PlayForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Component
public class PlayFormValidator extends BaseFormValidator {

    /**
     * @param paramClass
     * @author uratamanabu
     * @since 1.0
     */
    @Override
    public boolean supports(Class<?> paramClass) {
        return PlayForm.class.isAssignableFrom(paramClass);
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
        PlayForm form = (PlayForm) paramObject;
    }
}
