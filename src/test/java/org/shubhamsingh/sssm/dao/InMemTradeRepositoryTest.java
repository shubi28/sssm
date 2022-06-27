package org.shubhamsingh.sssm.dao;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.shubhamsingh.sssm.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemTradeRepositoryTest {

    private InMemTradeRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemTradeRepository();
    }

    private Trade getT6(final String indicator){
        return new Trade("trade-test6",
                System.currentTimeMillis() - (10 * 60 * 1000),
                90.0,
                90,
                "TEA",
                indicator);
    }

    private Trade getT7(final String indicator){
        return new Trade("trade-test7",
                System.currentTimeMillis() - (10 * 60 * 1000),
                670.0,
                90,
                "POP",
                indicator);
    }

    @Test
    void verify_findAll() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
    }

    @Test
    void verify_findAllWith1TradeId() {
        assertNotNull(repo.findAll());
        final Iterable<Trade> trades = repo.findAll(Lists.newArrayList("trade-test2"));
        assertNotNull(trades);
        assertEquals(1,Iterables.size(trades));
        assertEquals("trade-test2",trades.iterator().next().getTradeId());
    }

    @Test
    void verify_findAllWithMorethan1TradeId() {
        assertNotNull(repo.findAll());
        final Iterable<Trade> trades =
                repo.findAll(Lists.newArrayList("trade-test1","trade-test2","trade-test3"));
        assertNotNull(trades);
        assertEquals(3,Iterables.size(trades));
        final Set<String> tradeIds = StreamSupport.stream(trades.spliterator(),false)
                .map(s->s.getTradeId())
                .collect(Collectors.toSet());
        assertTrue(tradeIds.contains("trade-test1"));
        assertTrue(tradeIds.contains("trade-test2"));
        assertTrue(tradeIds.contains("trade-test3"));
    }

    @Test
    void verify_findById() {
        assertNotNull(repo.findAll());
        final Optional<Trade> trade = repo.findById("trade-test2");
        assertNotNull(trade);
        assertTrue(trade.get().getTradeId().equals("trade-test2"));
    }

    @Test
    void verify_save_insert() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
        final Trade teaSell = repo.save(getT6("sell"));
        assertEquals(6,Iterables.size(repo.findAll()));
        assertNotNull(teaSell);
        assertTrue(teaSell.getTradeId().equals("trade-test6"));
        final Optional<Trade> getTea = repo.findById("trade-test6");
        assertNotNull(getTea);
        assertTrue(getTea.get().getTradeId().equals("trade-test6"));
        assertEquals("TEA",getTea.get().getSymbol());

    }

    @Test
    void verify_save_insertMultiple() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
        final Iterable<Trade> trades =
                repo.save(Lists.newArrayList(
                getT6("sell"),
                getT7("buy")));
        assertEquals(7,Iterables.size(repo.findAll()));
        final Iterable<Trade> agTrades = repo.findAll(Lists.newArrayList("trade-test6","trade-test7"));
        assertNotNull(agTrades);
        final Set<String> tradeIds = StreamSupport.stream(agTrades.spliterator(),false)
                .map(s->s.getTradeId()).collect(Collectors.toSet());
        assertTrue(tradeIds.contains("trade-test6"));
        assertTrue(tradeIds.contains("trade-test7"));
    }

    @Test
    void verify_delete() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
        final Trade trade6 = repo.save(getT6("sell"));
        assertEquals(6,Iterables.size(repo.findAll()));
        assertNotNull(trade6);
        assertTrue(trade6.getTradeId().equals("trade-test6"));
        assertTrue(trade6.getSymbol().equals("TEA"));

        repo.delete(trade6);
        assertEquals(5,Iterables.size(repo.findAll()));
        final Optional<Trade> dtrade = repo.findById("trade-test6");
        assertFalse(dtrade.isPresent());
    }

}