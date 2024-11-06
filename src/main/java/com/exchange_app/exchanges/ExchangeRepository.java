package com.exchange_app.exchanges;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    void updateBalance(UUID id, @NotNull(message = "PLN balance cannot be null")
            @Min(value = 0, message = "PLN balance cannot be negative") BigDecimal pln,
            @NotNull(message = "USD balance cannot be null")
            @Min(value = 0, message = "USD balance cannot be negative") BigDecimal usd);
}
