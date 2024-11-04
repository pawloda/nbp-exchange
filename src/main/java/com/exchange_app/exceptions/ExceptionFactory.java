package com.exchange_app.exceptions;

import java.util.UUID;

public class ExceptionFactory {
    private ExceptionFactory() {
        //For util class
    }

    public static AccountNotFoundException throwAccountNotFoundException(UUID id) {
        throw new AccountNotFoundException("The account with ID " + id + " not found!");
    }

    public static void throwNotEnoughMoneyForInitException() {
        throw new NotEnoughMoneyException("You cannot set negative values for the account init balance!");
    }

    public static void throwNotEnoughMoneyForPlnExchangeException(UUID id) {
        throw new NotEnoughMoneyException("The account with ID " + id + " has not enough PLNs!");
    }

    public static void throwNotEnoughMoneyForUsdExchangeException(UUID id) {
        throw new NotEnoughMoneyException("The account with ID " + id + " has not enough USDs!");
    }
}
