package com.exchange_app.commons;

import org.springframework.data.util.Pair;

import java.util.UUID;

import static com.exchange_app.exceptions.ExceptionFactory.*;
import static java.util.Objects.isNull;

public class CurrencyPairValidator {
    private CurrencyPairValidator() {
        //For util class
    }

    public static void validateIfInitValueIsPositive(Double value) {
        if (validateIfPositiveValue(value)) {
            throwNotEnoughMoneyForInitException();
        }
    }

    public static void validateIfAccountBalanceExists(UUID id, Pair<Double, Double> balance) {
        if(isNull(balance)) {
            throwAccountNotFoundException(id);
        }
    }

    public static void validateIfAccountBalanceAllowsOperation(UUID id, Pair<Double, Double> balance) {
        if (validateIfPositiveValue(balance.getFirst())) {
            throwNotEnoughMoneyForPlnExchangeException(id);
        }
        if (validateIfPositiveValue(balance.getSecond())) {
            throwNotEnoughMoneyForUsdExchangeException(id);
        }
    }

    private static boolean validateIfPositiveValue(Double value) {
        return value >= 0;
    }
}
