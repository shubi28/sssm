package org.shubhamsingh.sssm.model.validator;

import org.shubhamsingh.sssm.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ValidFixedDividendValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @InjectMocks
    private ValidFixedDividendValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
    }

    public static Stock.StockBuilder preferredStockBuilder(){
        return Stock.builder().symbol("TEST").type("PREFERRED").lastDividend(0.0).parValue(0.0);
    }

    @Test
    public void verify_invalid_null_value(){
        assertEquals(false,validator.isValid(preferredStockBuilder().build(),context));
    }

    @Test
    public void verify_invalid_nonnull_ltzero_value(){
        assertEquals(false,validator.isValid(preferredStockBuilder().fixedDividend(-9.0).build(),context));
    }

    @Test
    public void verify_valid_nonnull_zero_value(){
        assertEquals(true,validator.isValid(preferredStockBuilder().fixedDividend(0.0).build(),context));
    }

    @Test
    public void verify_valid_nonnull_nonzero_value(){
        assertEquals(true,validator.isValid(preferredStockBuilder().fixedDividend(1.0).build(),context));
        assertEquals(true,validator.isValid(preferredStockBuilder().fixedDividend(1.4).build(),context));
        assertEquals(true,validator.isValid(preferredStockBuilder().fixedDividend(78.2).build(),context));
    }

    @Test
    public void verify_invalid_nonnull_gt100_value(){
        assertEquals(false,validator.isValid(preferredStockBuilder().fixedDividend(100000.0).build(),context));
    }

}