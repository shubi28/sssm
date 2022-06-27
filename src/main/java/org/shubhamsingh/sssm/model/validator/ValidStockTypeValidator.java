package org.shubhamsingh.sssm.model.validator;

import org.shubhamsingh.sssm.model.StockType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Rules for validating StockType for a Stock
 */
@Component
@Slf4j
public class ValidStockTypeValidator implements ConstraintValidator<ValidStockType,String> {


    @Override
    public boolean isValid(final String type, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if(type == null || type.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Type cannot be null/empty").addConstraintViolation();
            return false;
        }
        try {
            final StockType stockType = StockType.strToType(type);
            log.info("Stock Type is "+stockType.name());
            return true;
        }
        catch (NullPointerException  | IllegalArgumentException e){
            log.error("Invalid Enum value ["+type+"]");
            context.buildConstraintViolationWithTemplate("Type ["+type+"] is invalid").addConstraintViolation();
            return false;
        }
    }
}
