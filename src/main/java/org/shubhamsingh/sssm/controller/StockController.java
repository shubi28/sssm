package org.shubhamsingh.sssm.controller;

import org.shubhamsingh.sssm.dao.InMemRepository;
import org.shubhamsingh.sssm.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Crud operations for Stocks
 */
@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@Validated
public class StockController {

    private final InMemRepository<Stock,String> repository;

    @GetMapping(path = "/{symbol}")
    public Stock getStock(@PathVariable final String symbol){
        final Optional<Stock> stock = repository.findById(symbol);
        return stock.isPresent() ? stock.get() : null;
    }

    @GetMapping(path = "/list")
    public Iterable<Stock> list(){
        return repository.findAll();
    }

    @PutMapping
    public Stock addUpdateStock(
            @Valid
            @RequestBody final Stock stock){
        return repository.save(stock);
    }

}
