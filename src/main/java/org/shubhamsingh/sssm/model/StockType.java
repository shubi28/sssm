package org.shubhamsingh.sssm.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum StockType {

    COMMON,
    PREFERRED;

    public static StockType strToType(final String stockType) {
        StockType result = null;
        try {
            result = StockType.valueOf(stockType.toUpperCase());
        } catch (Exception e) {
            log.error("[" + stockType + "] is not a valid type");
        }
        return result;
    }

}
