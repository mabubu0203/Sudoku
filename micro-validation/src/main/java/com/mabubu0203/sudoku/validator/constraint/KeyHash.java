package com.mabubu0203.sudoku.validator.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Size(max = 64)
public @interface KeyHash {

    String message() default "KeyHash is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
