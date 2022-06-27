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

public class ValidStockTypeValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @InjectMocks
    private ValidStockTypeValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);

    }

    @Test
    public void verify_valid_common_type(){
        assertEquals(true,validator.isValid("common",context));
        assertEquals(true,validator.isValid("COMMON",context));
        assertEquals(true,validator.isValid("Common",context));
        assertEquals(true,validator.isValid("CoMmon",context));
    }

    @Test
    public void verify_valid_preferred_type(){
        assertEquals(true,validator.isValid("preferred",context));
        assertEquals(true,validator.isValid("PREFERRED",context));
        assertEquals(true,validator.isValid("Preferred",context));
        assertEquals(true,validator.isValid("preFeRRED",context));
    }

    @Test
    public void verify_invalid_null_type(){
        assertEquals(false,validator.isValid(null,context));
        assertEquals(false,validator.isValid("",context));

    }

}