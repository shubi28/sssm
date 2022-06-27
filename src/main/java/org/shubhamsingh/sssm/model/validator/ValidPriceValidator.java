package org.shubhamsingh.sssm.model.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Rules for validating StockPrice input for a Stock
 */
@Component
public class ValidPriceValidator implements ConstraintValidator<ValidPrice,Number> {

    @Override
    public boolean isValid(final Number value, final ConstraintValidatorContext constraintValidatorContext) {
        if(value == null)
            return false;
        if(value instanceof BigDecimal)
            return ((BigDecimal) value).compareTo(BigDecimal.ZERO) == 1;
        return value instanceof Double || value instanceof Float
                ? value.doubleValue() > 0
                : value.longValue() > 0;

    }
}
