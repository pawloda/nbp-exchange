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
        if (validateIfNegativeValue(value)) {
            throwNotEnoughMoneyForInitException();
        }
    }

    public static void validateIfAccountBalanceExists(UUID id, Pair<Double, Double> balance) {
        if(isNull(balance)) {
            throwAccountNotFoundException(id);
        }
    }

    public static void validateIfAccountBalanceAllowsOperation(UUID id, Pair<Double, Double> balance) {
        if (validateIfNegativeValue(balance.getFirst())) {
            throwNotEnoughMoneyForPlnExchangeException(id);
        }
        if (validateIfNegativeValue(balance.getSecond())) {
            throwNotEnoughMoneyForUsdExchangeException(id);
        }
    }

    private static boolean validateIfNegativeValue(Double value) {
        return value < 0;
    }
}
