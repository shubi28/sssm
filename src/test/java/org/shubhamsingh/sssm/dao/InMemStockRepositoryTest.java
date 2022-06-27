package org.shubhamsingh.sssm.dao;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.shubhamsingh.sssm.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class InMemStockRepositoryTest {

    private InMemStockRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemStockRepository();
    }

    @Test
    void verify_findAll() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
    }

    @Test
    void verify_findAllWith1Symbol() {
        assertNotNull(repo.findAll());
        final Iterable<Stock> stocks = repo.findAll(Lists.newArrayList("GIN"));
        assertNotNull(stocks);
        assertEquals(1,Iterables.size(stocks));
        assertEquals("GIN",stocks.iterator().next().getSymbol());
    }

    @Test
    void verify_findAllWithMorethan1Symbol() {
        assertNotNull(repo.findAll());
        final Iterable<Stock> stocks = repo.findAll(Lists.newArrayList("GIN","POP","ALE"));
        assertNotNull(stocks);
        assertEquals(3,Iterables.size(stocks));
        final Set<String> symbols = StreamSupport.stream(stocks.spliterator(),false)
                .map(s->s.getSymbol())
                .collect(Collectors.toSet());
        assertTrue(symbols.contains("GIN"));
        assertTrue(symbols.contains("POP"));
        assertTrue(symbols.contains("ALE"));
    }

    @Test
    void verify_findById() {
        assertNotNull(repo.findAll());
        final Optional<Stock> stock = repo.findById("GIN");
        assertNotNull(stock);
        assertTrue(stock.get().getSymbol().equals("GIN"));
    }

    @Test
    void verify_save_insert() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
        final Stock googlStock =
                repo.save(Stock.builder()
                        .symbol("GOOGL")
                        .type("common")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(100.0)
                        .build());
        assertEquals(6,Iterables.size(repo.findAll()));
        assertNotNull(googlStock);
        assertTrue(googlStock.getSymbol().equals("GOOGL"));
        final Optional<Stock> getGoogl = repo.findById("GOOGL");
        assertNotNull(getGoogl);
        assertTrue(getGoogl.get().getSymbol().equals("GOOGL"));
        assertEquals(100.0,getGoogl.get().getParValue());

    }

    @Test
    void verify_save_insertMultiple() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
        final Iterable<Stock> stocks = repo.save(Lists.newArrayList(
                Stock.builder().symbol("GOOGL").type("common").lastDividend(0.0).fixedDividend(0.0).parValue(100.0).build(),
                Stock.builder().symbol("APPL").type("preferred").lastDividend(0.0).fixedDividend(12.0).parValue(100.0).build()));
        assertEquals(7,Iterables.size(repo.findAll()));
        final Iterable<Stock> agStocks = repo.findAll(Lists.newArrayList("GOOGL","APPL"));
        assertNotNull(agStocks);
        final Set<String> symbols = StreamSupport.stream(agStocks.spliterator(),false).map(s->s.getSymbol()).collect(Collectors.toSet());
        assertTrue(symbols.contains("GOOGL"));
        assertTrue(symbols.contains("APPL"));
    }

    @Test
    void verify_save_update() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
        final Stock googlStock =
                repo.save(Stock.builder()
                        .symbol("GOOGL")
                        .type("common")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(100.0)
                        .build());
        assertEquals(6,Iterables.size(repo.findAll()));
        assertNotNull(googlStock);
        assertTrue(googlStock.getSymbol().equals("GOOGL"));
        assertTrue(googlStock.getType().equals("common"));

        final Stock googl2Stock =repo.save(googlStock.toBuilder().type("preferred").build());
        assertEquals(6,Iterables.size(repo.findAll()));
        assertNotNull(googl2Stock);
        assertTrue(googl2Stock.getSymbol().equals("GOOGL"));
        assertTrue(googl2Stock.getType().equals("preferred"));

    }

    @Test
    void verify_delete() {
        assertNotNull(repo.findAll());
        assertEquals(5, Iterables.size(repo.findAll()));
        final Stock googlStock =
                repo.save(Stock.builder()
                        .symbol("GOOGL")
                        .type("common")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(100.0)
                        .build());
        assertEquals(6,Iterables.size(repo.findAll()));
        assertNotNull(googlStock);
        assertTrue(googlStock.getSymbol().equals("GOOGL"));
        assertTrue(googlStock.getType().equals("common"));

        repo.delete(googlStock);
        assertEquals(5,Iterables.size(repo.findAll()));
        final Optional<Stock> gdStock = repo.findById("GOOGL");
        assertFalse(gdStock.isPresent());
    }
}