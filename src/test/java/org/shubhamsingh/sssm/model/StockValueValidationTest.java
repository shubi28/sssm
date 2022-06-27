package org.shubhamsingh.sssm.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StockValueValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private Stock aNonNullStock(){
        return Stock.builder()
                .symbol("TEST")
                .type("common")
                .lastDividend(0.0)
                .fixedDividend(0.0)
                .parValue(0.0)
                .build();
    }

    @Test
    public void verify_invalid_null_stockPrice(){
        final Set<ConstraintViolation<StockValue>> violations =
                validator.validate(new StockValue(aNonNullStock(), LocalDate.now(),null));
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_invalid_0_stockPrice(){
        final Set<ConstraintViolation<StockValue>> violations =
                validator.validate(new StockValue(aNonNullStock(), LocalDate.now(),0.0));
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_valid_gt0_stockPrice(){
        final Set<ConstraintViolation<StockValue>> violations =
                validator.validate(new StockValue(aNonNullStock(), LocalDate.now(),120.0));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_invalid_null_stock(){
        final Set<ConstraintViolation<StockValue>> violations =
                validator.validate(new StockValue(null, LocalDate.now(),1.0));
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_invalid_invalidStock(){
        final Set<ConstraintViolation<StockValue>> violations =
                validator.validate(new StockValue(aNonNullStock().toBuilder().symbol(null).build(), LocalDate.now(),1.0));
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_valid_validStock(){
        final Set<ConstraintViolation<StockValue>> violations =
                validator.validate(new StockValue(aNonNullStock(), LocalDate.now(),120.0));
        assertTrue(violations.isEmpty());
    }
}