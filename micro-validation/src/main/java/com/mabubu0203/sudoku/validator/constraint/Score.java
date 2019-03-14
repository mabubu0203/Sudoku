package com.mabubu0203.sudoku.validator.constraint;

import javax.validation.Payload;
import javax.validation.constraints.Digits;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Digits(integer = 7, fraction = 0)
public @interface Score {

    String message() default "Score is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
