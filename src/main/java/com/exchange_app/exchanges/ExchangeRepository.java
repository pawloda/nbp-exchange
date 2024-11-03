package com.exchange_app.exchanges;

import org.springframework.data.util.Pair;

import java.util.UUID;

interface ExchangeRepository {
    Pair<Double, Double> findPlnAndUsdById(UUID id);

    void updateBalance(UUID id, Double usd, Double pln);
}
