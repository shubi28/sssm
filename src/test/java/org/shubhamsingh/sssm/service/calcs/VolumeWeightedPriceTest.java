package org.shubhamsingh.sssm.service.calcs;

import com.google.common.collect.Lists;
import org.shubhamsingh.sssm.model.Trade;
import org.shubhamsingh.sssm.model.filter.TradeAgeFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.shubhamsingh.sssm.model.Indicator.BUY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class VolumeWeightedPriceTest {

    private VolumeWeightedPrice volumeWeightedPrice;

    @BeforeEach
    void setUp() {
        volumeWeightedPrice = new VolumeWeightedPrice();
    }

    @Test
    public void verify_volWeightedPrice15_SingleTrade(){
        TradeAgeFilter trade15MinsAgo = new TradeAgeFilter(15);
        assertNull(volumeWeightedPrice.apply(
                Lists.newArrayList(
                        new Trade(
                                "trade-test",
                                System.currentTimeMillis() - (20 * 60 * 1000),
                                29.0,
                                20,
                                "GOOGL",
                                BUY.name())),
                trade15MinsAgo));

        final Double vwp = volumeWeightedPrice.apply(
                Lists.newArrayList(
                        new Trade(
                                "trade-test",
                                System.currentTimeMillis() - (10 * 60 * 1000),
                                29.0,
                                20,
                                "GOOGL",
                                BUY.name())),
                trade15MinsAgo);
        assertNotNull(vwp);
        assertEquals(29,vwp);
    }

    @Test
    public void verify_volWeightedPrice15_MultipleTrades(){
        TradeAgeFilter trade15MinsAgo = new TradeAgeFilter(15);
        assertNull(volumeWeightedPrice.apply(
                Lists.newArrayList(
                        new Trade(
                                "trade-test1",
                                System.currentTimeMillis() - (20 * 60 * 1000),
                                29.0,
                                20,
                                "GOOGL",
                                BUY.name()),
                        new Trade(
                                "trade-test2",
                                System.currentTimeMillis() - (20 * 60 * 1000),
                                11.0,
                                20,
                                "GOOGL",
                                BUY.name())),
                trade15MinsAgo));

        final Double vwp = volumeWeightedPrice.apply(
                Lists.newArrayList(
                        new Trade("trade-test1",
                                System.currentTimeMillis() - (10 * 60 * 1000),
                                29.0,
                                20,
                                "GOOGL",
                                "buy"),
                        new Trade("trade-test2",
                                System.currentTimeMillis() - (10 * 60 * 1000),
                                11.0,
                                20,
                                "GOOGL",
                                "buy")),
                trade15MinsAgo);
        assertNotNull(vwp);
        assertEquals(20,vwp);
    }

    private Trade getGOOGL(final String indicator, final double tradePrice){
        return new Trade("trade-test",
                System.currentTimeMillis() - (10 * 60 * 1000),
                tradePrice,
                20,
                "GOOGL",
                indicator);
    }
}