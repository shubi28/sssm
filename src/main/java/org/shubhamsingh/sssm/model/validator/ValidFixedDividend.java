package org.shubhamsingh.sssm.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidFixedDividendValidator.class})
@Documented
public @interface ValidFixedDividend {

    String message() default "Fixed dividend value is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
