package com.exchange_app.exchanges;

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

    void exchange(UUID id, String cuurency, String sum) {
        LOGGER.info("Exchange request has been received for client with id: {}", id);
        service.exchange(id, Currency.valueOf(cuurency), Double.valueOf(sum));
    }
}
