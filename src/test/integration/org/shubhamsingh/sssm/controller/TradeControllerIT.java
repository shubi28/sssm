package org.shubhamsingh.sssm.controller;

import com.google.common.collect.Iterables;
import org.shubhamsingh.sssm.model.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TradeControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_listTrades() throws Exception {

        final ResponseEntity<Iterable<Trade>> tradesResponse =
                restTemplate.exchange(
                        "http://localhost:"+port+"/trade/list",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Iterable<Trade>>() {
                        });
        final Iterable<Trade> trades = tradesResponse.getBody();

        assertNotNull(trades);
        assertEquals(5,Iterables.size(trades));
    }

    @Test
    public void test_BuyTrades() throws Exception {

        final ResponseEntity<Trade> tradesResponse =
                restTemplate.exchange(
                        "http://localhost:"+port+"/trade/BUY/GOOGL?price=100.0&quantity=20",
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<Trade>() {});
        final Trade trade = tradesResponse.getBody();

        assertNotNull(trade);
        assertNotNull(trade.getTradeId());
        assertEquals("GOOGL",trade.getSymbol());

        final ResponseEntity<Iterable<Trade>> listResponse =
                restTemplate.exchange(
                        "http://localhost:"+port+"/trade/list",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Iterable<Trade>>() {
                        });
        final Iterable<Trade> trades = listResponse.getBody();

        assertNotNull(trades);
        assertEquals(6,Iterables.size(trades));

    }

    @Test
    public void test_SellTrades() throws Exception {

        final ResponseEntity<Trade> tradesResponse =
                restTemplate.exchange(
                        "http://localhost:"+port+"/trade/SELL/GOOGL?price=100.0&quantity=20",
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<Trade>() {});
        final Trade trade = tradesResponse.getBody();

        assertNotNull(trade);
        assertNotNull(trade.getTradeId());
        assertEquals("GOOGL",trade.getSymbol());

        final ResponseEntity<Iterable<Trade>> listResponse =
                restTemplate.exchange(
                        "http://localhost:"+port+"/trade/list",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Iterable<Trade>>() {
                        });
        final Iterable<Trade> trades = listResponse.getBody();

        assertNotNull(trades);
        assertEquals(6,Iterables.size(trades));

    }

    @Test
    public void test_VolumeWeightedStockPrice() throws Exception {

        final ResponseEntity<Double> response =
                restTemplate.getForEntity(new URL("http://localhost:"+port+"/trade/VolumeWeightedStockPrice/10").toString(),
                        Double.class);

        final Double volWgtPrice = response.getBody();

        assertNotNull(volWgtPrice);
        assertEquals(15.0,volWgtPrice);

    }

    @Test
    public void test_GBCEShareIndex() throws Exception {

        final ResponseEntity<Double> response =
                restTemplate.getForEntity(new URL("http://localhost:"+port+"/trade/GBCEShareIndex").toString(),
                        Double.class);

        final Double shareIndex = response.getBody();

        assertNotNull(shareIndex);
        final BigDecimal bd = BigDecimal.valueOf(shareIndex).setScale(3, RoundingMode.HALF_UP);
        assertEquals(44.735,bd.doubleValue());
    }


}