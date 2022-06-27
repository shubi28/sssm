package org.shubhamsingh.sssm.model.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ValidTradeIndicatorValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @InjectMocks
    private ValidTradeIndicatorValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);

    }

    @Test
    public void verify_valid_common_type() {
        assertEquals(true, validator.isValid("buy", context));
        assertEquals(true, validator.isValid("BUY", context));
        assertEquals(true, validator.isValid("Buy", context));
        assertEquals(true, validator.isValid("BuY", context));
    }

    @Test
    public void verify_valid_preferred_type() {
        assertEquals(true, validator.isValid("sell", context));
        assertEquals(true, validator.isValid("SELL", context));
        assertEquals(true, validator.isValid("Sell", context));
        assertEquals(true, validator.isValid("seLL", context));
    }

    @Test
    public void verify_invalid_null_type() {
        assertEquals(false, validator.isValid(null, context));
        assertEquals(false, validator.isValid("", context));

    }
}
