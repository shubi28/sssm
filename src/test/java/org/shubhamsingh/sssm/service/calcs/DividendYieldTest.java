package org.shubhamsingh.sssm.service.calcs;

import org.shubhamsingh.sssm.model.Stock;
import org.shubhamsingh.sssm.model.StockValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DividendYieldTest {

    private DividendYield dividendYield;

    @BeforeEach
    void setUp() {
        dividendYield = new DividendYield();
    }

    @Test
    public void verify_DY_CommonShare_ld0(){
        assertEquals(0,dividendYield.apply(
                new StockValue(
                        Stock.builder()
                                .symbol("TEA")
                                .type("COMMON")
                                .lastDividend(0.0)
                                .parValue(100.0)
                                .build(),
                        LocalDate.now(),
                        2.0)));
    }

    @Test
    public void verify_DY_CommonShare_ld100(){
        assertEquals(50.0,dividendYield.apply(
                new StockValue(
                        Stock.builder()
                                .symbol("TEA")
                                .type("COMMON")
                                .lastDividend(100.0)
                                .parValue(100.0)
                                .build(),
                        LocalDate.now(),
                        2.0)));
    }

    @Test
    public void verify_DY_PreferredShare_fd10(){
        assertEquals(5.0,dividendYield.apply(
                new StockValue(
                        Stock.builder()
                                .symbol("TEA")
                                .type("PREFERRED")
                                .lastDividend(0.0)
                                .fixedDividend(10.0)
                                .parValue(100.0)
                                .build(),
                        LocalDate.now(),
                        2.0)));
    }

    @Test
    public void verify_DY_PreferredShare_fd0(){
        assertEquals(0.0,dividendYield.apply(
                new StockValue(
                        Stock.builder()
                                .symbol("TEA")
                                .type("PREFERRED")
                                .lastDividend(0.0)
                                .fixedDividend(0.0)
                                .parValue(100.0)
                                .build(),
                        LocalDate.now(),
                        2.0)));
    }

    @Test
    public void verify_DY_PreferredShare_pricenull(){
        assertThrows(IllegalArgumentException.class,
                () -> dividendYield.apply(
                        new StockValue(
                                Stock.builder()
                                        .symbol("TEA")
                                        .type("PREFERRED")
                                        .lastDividend(0.0)
                                        .fixedDividend(40.0)
                                        .parValue(100.0)
                                        .build(),
                                LocalDate.now(),
                                null)));
    }
}