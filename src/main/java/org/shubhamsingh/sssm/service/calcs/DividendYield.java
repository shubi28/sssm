package org.shubhamsingh.sssm.service.calcs;

import org.shubhamsingh.sssm.model.StockType;
import org.shubhamsingh.sssm.model.StockValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Service to calculate DividendYield of stock at a given price
 *
 */
@Service
@Slf4j
public class DividendYield implements Function<StockValue,Double> {

    @Override
    public Double apply(final StockValue stockValue) {

        if(stockValue == null || stockValue.getPrice() == null || stockValue.getPrice() == 0)
            throw new IllegalArgumentException("Price cannot be Null/0"); //Throw exception to Fail-fast
                                                        // here as this value is used in other calculation
        switch (StockType.strToType(stockValue.getStock().getType())){
            case COMMON:
                log.debug(String.format("Calculating DividendYield of Common share " +
                        "for [%s] with LastDividend [%s] at price [%s]", stockValue.getStock().getSymbol(),
                        stockValue.getStock().getLastDividend(), stockValue.getPrice()));
                return stockValue.getStock().getLastDividend() / stockValue.getPrice();
            case PREFERRED:
                log.debug(String.format("Calculating DividendYield of Preferred share " +
                        "for [%s] with FixedDividend [%s] and ParValue [%s] " +
                        " at price [%s]", stockValue.getStock().getSymbol(), stockValue.getStock().getFixedDividend(),
                        stockValue.getStock().getParValue(),stockValue.getPrice()));
                return (stockValue.getStock().getFixedDividend() * stockValue.getStock().getParValue()) /
                        (stockValue.getPrice() * 100.0) ;
            default:
                throw new RuntimeException("Unrecognized share type ["+ stockValue.getStock().getType()+"] " +
                        "for DividendYield calculation");
        }

    }
}
