package org.shubhamsingh.sssm.controller;

import org.shubhamsingh.sssm.dao.InMemStockRepository;
import org.shubhamsingh.sssm.model.Stock;
import org.shubhamsingh.sssm.model.StockValue;
import org.shubhamsingh.sssm.model.validator.ValidPrice;
import org.shubhamsingh.sssm.service.calcs.DividendYield;
import org.shubhamsingh.sssm.service.calcs.GeometricMeanPrice;
import org.shubhamsingh.sssm.service.calcs.PERatio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/stockValue")
@RequiredArgsConstructor
public class StockValueController {

    private final DividendYield dividendYield;

    private final PERatio peRatio;

    private final GeometricMeanPrice geometricMeanPrice;

    private final InMemStockRepository stockRepository;

    @GetMapping(path = "/{symbol}/dividendYield")
    public Double calculateDividendYield(
            @PathVariable @NotNull final String symbol,
            @RequestParam @ValidPrice final Double price){

        final Optional<Stock> stock = stockRepository.findById(symbol);
        if(stock.isPresent())
            return dividendYield.apply(new StockValue(stock.get(), LocalDate.now(), price));

        return null;
    }

    @GetMapping(path = "/{symbol}/peRatio")
    public Double calculatePERatio(
            @PathVariable @NotNull final String symbol,
            @RequestParam @ValidPrice final Double price){

        final Optional<Stock> stock = stockRepository.findById(symbol);
        if(stock.isPresent())
            return peRatio.apply(new StockValue(stock.get(), LocalDate.now(), price));

        return null;
    }
}
