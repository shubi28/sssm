package org.shubhamsingh.sssm.model;

import org.shubhamsingh.sssm.model.validator.ValidPrice;
import org.shubhamsingh.sssm.model.validator.ValidTradeIndicator;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class Trade {

    @NotNull
    private String tradeId;

    @NotNull
    private Long tradeTimestamp;

    @ValidPrice
    private Double tradePrice;

    @Min(value = 1)
    private Integer quantity;

    @NotNull
    private String symbol;

    @ValidTradeIndicator
    private String tradeIndicator;

}
