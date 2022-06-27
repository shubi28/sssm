package org.shubhamsingh.sssm.controller;

import com.google.common.collect.Iterables;
import org.shubhamsingh.sssm.dao.InMemRepository;
import org.shubhamsingh.sssm.model.Stock;
import org.shubhamsingh.sssm.model.StockValue;
import org.shubhamsingh.sssm.model.Trade;
import org.shubhamsingh.sssm.model.filter.TradeAgeFilter;
import org.shubhamsingh.sssm.service.calcs.GeometricMeanPrice;
import org.shubhamsingh.sssm.service.calcs.VolumeWeightedPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.shubhamsingh.sssm.model.Indicator.BUY;
import static org.shubhamsingh.sssm.model.Indicator.SELL;

/**
 * Interface to recording Trades, listing
 */
@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
@Validated
public class TradeController {

    private final InMemRepository<Trade,String> repository;

    private final InMemRepository<Stock,String> stockRepository;

    private final VolumeWeightedPrice volumeWeightedPrice;

    private final GeometricMeanPrice geometricMeanPrice;

    @GetMapping(path = "/list")
    public Iterable<Trade> list(@RequestParam(required = false) final Integer timeInMinutes) {
        if (timeInMinutes != null && timeInMinutes > 0){
            final TradeAgeFilter tradeAgeFilter = new TradeAgeFilter(timeInMinutes);
            final Iterable<Trade> trades = repository.findAll();
            return StreamSupport.stream(trades.spliterator(),false)
                    .filter(t->tradeAgeFilter.test(t))
                    .collect(Collectors.toList());
        }
        return repository.findAll();
    }

    @GetMapping(path = "/VolumeWeightedStockPrice/{timeInMinutes}")
    public Double volumeWeightedStockPrice(@PathVariable final Integer timeInMinutes) {
        return volumeWeightedPrice.apply(repository.findAll(),new TradeAgeFilter(timeInMinutes));
    }

    @GetMapping(path = "/GBCEShareIndex")
    public Double calculateGBCEShareIndex(){
        final Iterable<Trade> allTrades = repository.findAll();
        if(Iterables.isEmpty(allTrades))
            return null;
        return geometricMeanPrice.apply(StreamSupport.stream(allTrades.spliterator(),false)
                .map(t->{
                    final Optional<Stock> stock = stockRepository.findById(t.getSymbol());
                    if(stock.isPresent()){
                        return new StockValue(stock.get(),
                                LocalDateTime.ofInstant(Instant.ofEpochSecond(t.getTradeTimestamp()),
                                        TimeZone.getDefault().toZoneId()).toLocalDate(),
                                t.getTradePrice());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    @PostMapping(path = "/BUY/{symbol}")
    public Trade buy(
            @PathVariable final String symbol,
            @RequestParam final Double price,
            @RequestParam final Integer quantity){
        return repository.save(
                new Trade(
                        UUID.randomUUID().toString(),
                        System.currentTimeMillis(),
                        price,
                        quantity,
                        symbol,
                        BUY.name()));
    }

    @PostMapping(path = "/SELL/{symbol}")
    public Trade sell(
            @PathVariable final String symbol,
            @RequestParam final Double price,
            @RequestParam final Integer quantity){
        return repository.save(
                new Trade(
                        UUID.randomUUID().toString(),
                        System.currentTimeMillis(),
                        price,
                        quantity,
                        symbol,
                        SELL.name()));
    }
}
