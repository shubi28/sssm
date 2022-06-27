package org.shubhamsingh.sssm.service.calcs;

import com.google.common.collect.Iterables;
import org.shubhamsingh.sssm.model.StockValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.function.Function;


/**
 * GeometricMeanPrice service calculates the GBCE All Share Index
 * using the geometric mean of prices for all stocks
 *
 */
@Service
@Slf4j
@Validated
public class GeometricMeanPrice implements Function<Iterable<StockValue>,Double> {

    @Override
    public Double apply(final Iterable<StockValue> stockValues) {

        if(stockValues == null || Iterables.size(stockValues) == 0)
            return null; //returning null rather than exception as at moment this

        Double productPrice = 1.0;
        for (final StockValue stockValue : stockValues) {
            productPrice *= stockValue.getPrice();
        }

        final double exp = 1.0 / Iterables.size(stockValues);
        return Math.pow(productPrice,exp);
    }
}
