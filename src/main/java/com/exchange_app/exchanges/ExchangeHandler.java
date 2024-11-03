package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ExchangeHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeHandler.class);

    private final ExchangeService service;

    ExchangeHandler(final ExchangeService service) {
        this.service = service;
    }

    void exchange(UUID id, String currencyToExchange, String sum) throws JsonProcessingException {
        LOGGER.info("Exchange request has been received for client with id: {}", id);
        service.exchange(id, Currency.valueOf(currencyToExchange.toUpperCase()), Double.valueOf(sum));
    }
}
