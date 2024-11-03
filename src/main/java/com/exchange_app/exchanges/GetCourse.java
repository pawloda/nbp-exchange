package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetCourse {
    private static final Integer ONE = 1;
    private static final String NBP_URL = "https://api.nbp.pl/api/exchangerates/rates/c/usd/last/10/?format=json";
    private static final String OPERATION_ASK = "ask";
    private static final String OPERATION_BID = "bid";
    private static final String RATES = "rates";

    private final RestTemplate template;

    GetCourse(final RestTemplate template) {
        this.template = template;
    }

    Double forPLNExchange() throws JsonProcessingException {
        return ONE / getUSDtoPLNRateForOperation(OPERATION_ASK);
    }

    Double forUSDExchange() throws JsonProcessingException {
        return getUSDtoPLNRateForOperation(OPERATION_BID);
    }

    private double getUSDtoPLNRateForOperation(String operationType) throws JsonProcessingException {
        var response = sendGetRequest();
        var rootNode = new ObjectMapper().readTree(response);
        var rates = rootNode.path(RATES);
        var newestRate = rates.get(rates.size() - ONE);
        return newestRate.path(operationType).asDouble();
    }

    private String sendGetRequest() {
        return template.getForObject(NBP_URL, String.class);
    }
}
