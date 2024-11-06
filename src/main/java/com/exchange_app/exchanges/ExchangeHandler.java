package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
class ExchangeHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeHandler.class);

    private final ExchangeServiceImpl service;

    ExchangeHandler(final ExchangeServiceImpl service) {
        this.service = service;
    }

    void exchange(UUID id, String currencyToExchange, String sum) throws JsonProcessingException {
        LOGGER.info("Exchange request has been received for client with id: {}", id);
        var currency = Currency.valueOf(currencyToExchange.toUpperCase());
        service.exchange(id, currency, BigDecimal.valueOf(Double.parseDouble(sum)));
    }
}
