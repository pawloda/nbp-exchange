package com.exchange_app.commons;

import com.exchange_app.exceptions.NotEnoughMoneyException;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.UUID;

public class CurrencyPairValidator {
    private CurrencyPairValidator() {
        //For util class
    }

    public static void validateIfAccountBalanceAllowsOperation(UUID id, Pair<BigDecimal, BigDecimal> balance) {
        if (validateIfNegativeValue(balance.getFirst())) {
            throw new NotEnoughMoneyException("The account with ID " + id + " has not enough PLNs!");
        }
        if (validateIfNegativeValue(balance.getSecond())) {
            throw new NotEnoughMoneyException("The account with ID " + id + " has not enough USDs!");
        }
    }

    public static boolean validateIfNegativeValue(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }
}
