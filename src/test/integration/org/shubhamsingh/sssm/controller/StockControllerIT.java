package org.shubhamsingh.sssm.controller;

import com.google.common.collect.Iterables;
import org.shubhamsingh.sssm.model.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StockControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_listStocks() throws Exception {

        final ResponseEntity<Iterable<Stock>> response =
                restTemplate.exchange(
                        "http://localhost:"+port+"/stock/list",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Iterable<Stock>>() {});
        final Iterable<Stock> stocks = response.getBody();

        assertNotNull(stocks);
        assertEquals(5, Iterables.size(stocks));
    }

    @Test
    public void test_getStock() throws Exception {

        final ResponseEntity<Stock> response =
                restTemplate.exchange(
                        "http://localhost:"+port+"/stock/TEA",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Stock>() {});
        final Stock stock = response.getBody();

        assertNotNull(stock);
        assertEquals("TEA", stock.getSymbol());
    }

    @Test
    public void test_addStock() throws Exception {

        restTemplate.put(new URL("http://localhost:"+port+"/stock").toString(),
                        Stock.builder()
                                .symbol("GOOGLE")
                                .type("preferred")
                                .lastDividend(0.0)
                                .fixedDividend(0.0)
                                .parValue(100.0)
                                .build());


        final ResponseEntity<Stock> response =
                restTemplate.exchange(
                        "http://localhost:"+port+"/stock/TEA",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Stock>() {});
        final Stock stock = response.getBody();

        assertNotNull(stock);
        assertEquals("TEA", stock.getSymbol());
    }

    @Test
    public void test_updateStock() throws Exception {

        restTemplate.put(new URL("http://localhost:"+port+"/stock").toString(),
                Stock.builder()
                        .symbol("GOOGL")
                        .type("preferred")
                        .lastDividend(0.0)
                        .fixedDividend(0.0)
                        .parValue(100.0)
                        .build());


        final ResponseEntity<Stock> response =
                restTemplate.exchange(
                        "http://localhost:"+port+"/stock/GOOGL",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Stock>() {});
        final Stock stock = response.getBody();

        assertNotNull(stock);
        assertEquals("GOOGL", stock.getSymbol());
        assertEquals(100.0, stock.getParValue());


        restTemplate.put(new URL("http://localhost:"+port+"/stock").toString(),
                stock.toBuilder().parValue(150.0).build());

        final ResponseEntity<Stock> updatedResponse =
                restTemplate.exchange(
                        "http://localhost:"+port+"/stock/GOOGL",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Stock>() {});
        final Stock updatedStock = updatedResponse.getBody();

        assertNotNull(updatedStock);
        assertEquals("GOOGL", updatedStock.getSymbol());
        assertEquals(150.0, updatedStock.getParValue());


    }

}