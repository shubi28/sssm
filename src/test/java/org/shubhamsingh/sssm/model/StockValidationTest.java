package org.shubhamsingh.sssm.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void verify_invalid_null_stockSymbol(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol(null)
                        .type("common")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_invalid_empty_stockSymbol(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("")
                        .type("common")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_valid_nonnull_nonempty_stockSymbol(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("APPL")
                        .type("common")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_invalid_null_stockType(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type(null)
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_invalid_empty_stockType(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }

    @Test
    public void verify_invalid_nonempty_stockType(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("test")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertEquals(1,violations.size());
        violations.stream().forEach(e->System.out.println(e.getMessage()));
    }


    @Test
    public void verify_valid_nonempty_nonnull_common_stockType(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("common")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_valid_nonempty_nonnull_preferred_stockType(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_invalid_null_lastdividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(null)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 1);
    }

    @Test
    public void verify_invalid_nonnull_ltzero_lastdividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(-43.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 1);
    }

    @Test
    public void verify_valid_nonnull_zero_lastdividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_valid_nonnull_gtzero_lastdividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(100345340.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_invalid_null_fixeddividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(null)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 1);
    }

    @Test
    public void verify_invalid_nonnull_ltzero_fixeddividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(-25.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 1);
    }

    @Test
    public void verify_valid_nonnull_zero_fixeddividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_valid_nonnull_100_fixeddividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(100345340.0)
                        .fixedDividend(100.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_valid_nonnull_gt100_fixeddividend(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(100345340.0)
                        .fixedDividend(140.0)
                        .parValue(0.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size()==1);
    }


    @Test
    public void verify_invalid_null_parvalue(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(null)
                        .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 1);
    }

    @Test
    public void verify_invalid_nonnull_ltzero_parvalue(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(-6.0)
                        .build());
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 1);
    }

    @Test
    public void verify_valid_nonnull_zero_parvalue(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(0.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

    @Test
    public void verify_valid_nonnull_gtzero_parvalue(){
        final Set<ConstraintViolation<Stock>> violations =
                validator.validate(Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(23.0)
                        .build());
        assertTrue(violations.isEmpty());
    }

}