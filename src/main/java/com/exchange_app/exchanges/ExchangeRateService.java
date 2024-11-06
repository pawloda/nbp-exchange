package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeRateService {
    private static final Integer ONE = 1;
    private static final Integer SCALE = 10;
    private static final String NBP_URL = "https://api.nbp.pl/api/exchangerates/rates/c/usd/last/10/?format=json";
    private static final String OPERATION_ASK = "ask";
    private static final String OPERATION_BID = "bid";
    private static final String RATES = "rates";

    private final RestTemplate template;

    ExchangeRateService(final RestTemplate template) {
        this.template = template;
    }

    BigDecimal forPLNExchange() throws JsonProcessingException {
        return BigDecimal.ONE.divide(getUSDtoPLNRateForOperation(OPERATION_ASK), SCALE, RoundingMode.HALF_UP);
    }

    BigDecimal forUSDExchange() throws JsonProcessingException {
        return getUSDtoPLNRateForOperation(OPERATION_BID);
    }

    private BigDecimal getUSDtoPLNRateForOperation(String operationType) throws JsonProcessingException {
        var response = sendGetRequest();
        var rootNode = new ObjectMapper().readTree(response);
        var rates = rootNode.path(RATES);
        var newestRate = rates.get(rates.size() - ONE);
        return BigDecimal.valueOf(newestRate.path(operationType).asDouble());
    }

    private String sendGetRequest() {
        return template.getForObject(NBP_URL, String.class);
    }
}
