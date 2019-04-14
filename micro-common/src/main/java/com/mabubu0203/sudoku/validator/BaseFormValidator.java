package com.mabubu0203.sudoku.validator;


import com.mabubu0203.sudoku.form.BaseForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * フォームバリデーションクラスのベースクラスです。<br>
 * このクラスを継承して実装してください。<br>
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Deprecated
public abstract class BaseFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return BaseForm.class.isAssignableFrom(paramClass);
    }

    @Override
    public void validate(Object paramObject, Errors errors) {
    }
}
