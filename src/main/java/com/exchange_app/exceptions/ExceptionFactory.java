package com.exchange_app.exceptions;

import java.util.UUID;

public class ExceptionFactory {
    private ExceptionFactory() {
        //For util class
    }

    public static AccountNotFoundException throwAccountNotFoundException(UUID id) {
        throw new AccountNotFoundException("The account with ID " + id + " not found!");
    }

    public static AccountNotFoundException throwNotEnoughMoneyForInitException() {
        throw new NotEnoughMoneyException("You cannot set negative values for the account init balance!");
    }

    public static AccountNotFoundException throwNotEnoughMoneyForPlnExchangeException(UUID id) {
        throw new NotEnoughMoneyException("The account with ID " + id + " has not enough PLNs!");
    }

    public static AccountNotFoundException throwNotEnoughMoneyForUsdExchangeException(UUID id) {
        throw new NotEnoughMoneyException("The account with ID " + id + " has not enough USDs!");
    }
}
