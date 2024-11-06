package com.exchange_app.exchanges;

import jakarta.transaction.Transactional;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
interface ExchangeRepository {
    @Transactional
    Optional<Pair<BigDecimal, BigDecimal>> findPlnAndUsdById(UUID id);

    @Transactional
    void updateBalance(UUID id, BigDecimal usd, BigDecimal pln);
}
