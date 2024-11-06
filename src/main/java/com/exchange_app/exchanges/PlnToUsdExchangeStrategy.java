package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

class PlnToUsdExchangeStrategy implements ExchangeStrategy {
    private final ExchangeRateService exchangeRateService;

    PlnToUsdExchangeStrategy(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public Pair<BigDecimal, BigDecimal> calculateExchange(Pair<BigDecimal, BigDecimal> plnAndUsd, BigDecimal sum)
            throws JsonProcessingException {
        var course = exchangeRateService.forPLNExchange();
        var pln = plnAndUsd.getFirst().subtract(sum);
        var usd = plnAndUsd.getSecond().add(sum.multiply(course));
        return Pair.of(pln, usd);
    }
}
