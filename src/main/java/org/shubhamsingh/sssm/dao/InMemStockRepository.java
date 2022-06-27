package org.shubhamsingh.sssm.dao;

import com.google.common.collect.Lists;
import org.shubhamsingh.sssm.model.Stock;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Test repository to store stocks
 */
@Repository
public class InMemStockRepository implements InMemRepository<Stock,String> {

    private final ConcurrentHashMap<String,Stock> stocks;

    public InMemStockRepository(){
        this.stocks = new ConcurrentHashMap<>();
        this.save(initStocks());
    }

    @Override
    public Iterable<Stock> findAll() {
        return this.stocks.values();
    }

    @Override
    public Iterable<Stock> findAll(final Iterable<String> symbols) {
        return StreamSupport.stream(symbols.spliterator(),true)
                .map(sym->this.stocks.get(sym))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Stock> findById(final String symbol) {
        return Optional.ofNullable(this.stocks.get(symbol));
    }

    @Override
    public Stock save(final Stock entity) {
        return this.stocks.merge(entity.getSymbol(),entity,(s1,s2)->s2);
    }

    @Override
    public Iterable<Stock> save(final Iterable<Stock> entities) {
        return StreamSupport.stream(entities.spliterator(),false)
                .map(this::save)
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(final Stock entity) {
        this.stocks.remove(entity.getSymbol());
    }

    public static Iterable<Stock> initStocks(){
        return Lists.newArrayList(
                Stock.builder().symbol("TEA").type("common").lastDividend(0.0).parValue(100.0).build(),
                Stock.builder().symbol("POP").type("common").lastDividend(8.0).parValue(100.0).build(),
                Stock.builder().symbol("ALE").type("common").lastDividend(23.0).parValue(60.0).build(),
                Stock.builder().symbol("GIN").type("preferred").lastDividend(8.0).fixedDividend(2.0).parValue(100.0).build(),
                Stock.builder().symbol("JOE").type("common").lastDividend(13.0).parValue(250.0).build());
    }

}
