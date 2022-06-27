package org.shubhamsingh.sssm.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Indicator {

    BUY,SELL;

    public static Indicator strToIndicator(final String indicator){
        Indicator result = null;
        try {
            result = Indicator.valueOf(indicator.toUpperCase());
        } catch (Exception e) {
            log.error("[" + indicator + "] is not a valid trade indicator");
        }
        return result;
    }
}
