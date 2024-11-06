package com.exchange_app.accounts;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

record Account(UUID id, String firstName, String secondName, BigDecimal pln, BigDecimal usd) {
    Account {
        if (isNull(usd)) {
            usd = BigDecimal.ZERO;
        }
    }
}
