package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class UsdToPlnExchangeStrategy implements ExchangeStrategy {
    private final ExchangeRateService exchangeRateService;

    UsdToPlnExchangeStrategy(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public Pair<BigDecimal, BigDecimal> calculateExchange(Pair<BigDecimal, BigDecimal> plnAndUsd, BigDecimal sum)
            throws JsonProcessingException {
        var course = exchangeRateService.forUSDExchange();
        var pln = plnAndUsd.getFirst().add(sum.multiply(course));
        var usd = plnAndUsd.getSecond().subtract(sum);
        return Pair.of(pln, usd);
    }
}
