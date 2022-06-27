package org.shubhamsingh.sssm.dao;

import com.google.common.collect.Lists;
import org.shubhamsingh.sssm.model.Trade;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.shubhamsingh.sssm.model.Indicator.BUY;
import static org.shubhamsingh.sssm.model.Indicator.SELL;


/**
 * Test repository to store trades
 */
@Repository
public class InMemTradeRepository implements InMemRepository<Trade, String> {

    private final ConcurrentHashMap<String,Trade> trades;

    public InMemTradeRepository(){
        this.trades = new ConcurrentHashMap<>();
        this.save(initTrades());
    }

    @Override
    public Iterable<Trade> findAll() {
        return this.trades.values();
    }

    @Override
    public Iterable<Trade> findAll(final Iterable<String> tradeIds) {
        return StreamSupport.stream(tradeIds.spliterator(),true)
                .map(sym->this.trades.get(sym))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Trade> findById(final String tradeId) {
        return Optional.ofNullable(this.trades.get(tradeId));
    }

    @Override
    public Trade save(final Trade entity) {
        return this.trades.merge(entity.getTradeId(),entity,(t1,t2)->t2);
    }

    @Override
    public Iterable<Trade> save(final Iterable<Trade> entities) {
        return StreamSupport.stream(entities.spliterator(),false)
                .map(this::save)
                .collect(Collectors.toSet());
    }

    @Override
    public void delete(final Trade entity) {
        this.trades.remove(entity.getTradeId());
    }

    private static List<Trade> initTrades(){
        return Lists.newArrayList(
                new Trade(
                        "trade-test1",
                        System.currentTimeMillis() - (10 * 60 * 1000),
                        52.0,
                        670,
                        "JOE",
                        SELL.name()),
                new Trade(
                        "trade-test2",
                        System.currentTimeMillis() - (10 * 60 * 1000),
                        88.0,
                        15,
                        "TEA",
                        SELL.name()),
                new Trade(
                        "trade-test3",
                        System.currentTimeMillis() - (1 * 60 * 1000),
                        15.0,
                        300,
                        "POP",
                        SELL.name()),
                new Trade(
                        "trade-test4",
                        System.currentTimeMillis() - (30 * 60 * 1000),
                        29.0,
                        70,
                        "GIN",
                        BUY.name()),
                new Trade(
                        "trade-test5",
                        System.currentTimeMillis() - (10 * 60 * 1000),
                        90.0,
                        90,
                        "ALE",
                        BUY.name()));
    }
}
