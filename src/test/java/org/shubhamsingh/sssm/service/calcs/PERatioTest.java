package org.shubhamsingh.sssm.service.calcs;

import org.shubhamsingh.sssm.model.Stock;
import org.shubhamsingh.sssm.model.StockValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PERatioTest {

    private PERatio peRatio;

    @BeforeEach
    void setUp() {
        peRatio = new PERatio(new DividendYield());
    }

    @Test
    public void verify_value_nonNullPrice1(){
        final Stock stock = Stock.builder()
                .symbol("TEST")
                .type("PREFERRED")
                .lastDividend(0.0)
                .fixedDividend(10.0)
                .parValue(100.0)
                .build();
        final Double per = peRatio.apply(new StockValue(stock, LocalDate.now(),2.0));
        assertEquals(0.4,per);
    }

    @Test
    public void verify_value_nonNullPrice2(){
        final Stock stock = Stock.builder()
                .symbol("TEA")
                .type("COMMON")
                .lastDividend(100.0)
                .parValue(100.0)
                .build();
        assertEquals(0.04,peRatio.apply(new StockValue(stock, LocalDate.now(),2.0)));
    }
}