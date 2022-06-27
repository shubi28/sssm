package org.shubhamsingh.sssm.service.calcs;

import com.google.common.collect.Iterables;
import org.shubhamsingh.sssm.model.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service to calculate VolumeWeightedPrice Stock price based on Trade of specified time limit
 */
@Service
@Slf4j
public class VolumeWeightedPrice implements BiFunction<Iterable<Trade>, Predicate<Trade>,Double> {

    @Override
    public Double apply(final Iterable<Trade> trades, final Predicate<Trade> tradeReq) {
        if(trades == null || Iterables.size(trades) == 0)
            return null;
        final List<Trade> filteredTrades = StreamSupport.stream(trades.spliterator(),false)
                .filter(t->tradeReq.test(t))
                .collect(Collectors.toList());
        if(filteredTrades.size() == 0){
            log.debug("No trades match the input specification for Trade age");
            return null;
        }

        log.debug(String.format("%d Trades matched the input specification for Trade age", filteredTrades.size()));
        final Double totalQuantity = filteredTrades.stream().collect(Collectors.summingDouble(Trade::getQuantity));
        double weightedSum = filteredTrades.stream()
                .map(trade-> (trade.getTradePrice() * trade.getQuantity()))
                .mapToDouble(Double::doubleValue)
                .sum();
        return weightedSum/totalQuantity;
    }
}
