package com.exchange_app.exchanges;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class ExchangeService {
    private final ExchangeRepository repository;

    ExchangeService(final ExchangeRepository repository) {
        this.repository = repository;
    }

    void exchange(UUID id, Currency currency, Double sum) {
        repository.updateBalance(id, currency, 0.0, 0.0);
    }
}
