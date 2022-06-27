package org.shubhamsingh.sssm.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StockValueControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_dividendYield() throws Exception {

        final ResponseEntity<Double> response =
                restTemplate.getForEntity(
                        new URL("http://localhost:"+port+"/stockValue/ALE/dividendYield?price=100.0").toString(),
                        Double.class);

        final Double dividendYield = response.getBody();

        assertNotNull(dividendYield);
        assertEquals(0.23,dividendYield);
    }

    @Test
    public void test_peRatio() throws Exception {

        final ResponseEntity<Double> response =
                restTemplate.getForEntity(
                        new URL("http://localhost:"+port+"/stockValue/ALE/peRatio?price=100.0").toString(),
                        Double.class);

        final Double peRatio = response.getBody();
        final BigDecimal bd = BigDecimal.valueOf(peRatio).setScale(3, RoundingMode.HALF_UP);
        assertEquals(434.783,bd.doubleValue());
    }

}