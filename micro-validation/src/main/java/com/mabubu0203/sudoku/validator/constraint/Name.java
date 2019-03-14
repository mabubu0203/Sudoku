package com.mabubu0203.sudoku.validator.constraint;

import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Size(max = 64)
public @interface Name {

    String message() default "KeyHash is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
