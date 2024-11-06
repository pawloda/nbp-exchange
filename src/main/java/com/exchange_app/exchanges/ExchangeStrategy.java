package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

public interface ExchangeStrategy {
    Pair<BigDecimal, BigDecimal> calculateExchange(Pair<BigDecimal, BigDecimal> plnAndUsd, BigDecimal sum)
            throws JsonProcessingException;
}
