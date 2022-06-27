package org.shubhamsingh.sssm.model.filter;

import org.shubhamsingh.sssm.model.Trade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeAgeFilterTest {

    private static Integer ONE_MINUTE = 1 * 60 * 1000;
    private static Integer TWO_MINUTES = 2 * 60 * 1000;
    private static Integer FIVE_MINUTES = 5 * 60 * 1000;
    private static Integer TEN_MINUTES = 10 * 60 * 1000;
    private static Integer FIFTEEN_MINUTES = 15 * 60 * 1000;
    private static Integer TWENTY_MINUTES = 20 * 60 * 1000;

    private Trade getT6(final long timestamp){
        return new Trade("trade-test6",
                timestamp,
                90.0,
                90,
                "TEA",
                "sell");
    }

    @Test
    public void verify_tradeAge1Minute_tradeTime2Minutes() {
        final TradeAgeFilter filter = new TradeAgeFilter(1);
        final Trade tade = getT6(System.currentTimeMillis() - TWO_MINUTES);
        assertEquals(false,filter.test(tade));

    }

    @Test
    public void verify_tradeAge1Minute_tradeTime1Minutes() {
        final TradeAgeFilter filter = new TradeAgeFilter(1);
        final Trade tade = getT6(System.currentTimeMillis() - ONE_MINUTE);
        assertEquals(true,filter.test(tade));

    }

    @Test
    public void verify_tradeAge15Minute_tradeTime20Minutes() {
        final TradeAgeFilter filter = new TradeAgeFilter(15);
        final Trade tade = getT6(System.currentTimeMillis() - TWENTY_MINUTES);
        assertEquals(false,filter.test(tade));

    }

    @Test
    public void verify_tradeAge15Minute_tradeTime1Minutes() {
        final TradeAgeFilter filter = new TradeAgeFilter(15);
        final Trade tade = getT6(System.currentTimeMillis() - TEN_MINUTES);
        assertEquals(true,filter.test(tade));

    }
}