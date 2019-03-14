package com.mabubu0203.sudoku.web.form.validator;

import com.mabubu0203.sudoku.validator.BaseFormValidator;
import com.mabubu0203.sudoku.web.form.CreateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Component
public class CreateFormValidator extends BaseFormValidator {

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Override
    public boolean supports(Class<?> paramClass) {
        return CreateForm.class.isAssignableFrom(paramClass);
    }

    /**
     * @author uratamanabu
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void validate(Object paramObject, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        CreateForm form = (CreateForm) paramObject;
        int selectType = form.getSelectType();
        if (selectType == 0) {
            errors.rejectValue("selectType", "default");
        } else if (selectType == 4 || selectType == 9) {
            // nothing to do
        } else {
            errors.rejectValue("selectType", "else");
        }
        String selectLevel = form.getSelectLevel();
        if (selectLevel.isEmpty()) {
            errors.rejectValue("selectLevel", "default");
        } else if ("easy".equals(selectLevel)
                || "normal".equals(selectLevel)
                || "hard".equals(selectLevel)) {
            // nothing to do
        } else {
            errors.rejectValue("selectLevel", "else");
        }
    }
}
