package com.exchange_app.accounts;

import com.exchange_app.exceptions.NotEnoughMoneyException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.exchange_app.commons.CurrencyPairValidator.validateIfNegativeValue;

@Service
interface AccountService {
    @Transactional
    UUID createAccount(Account account);

    @Transactional
    Account getAccount(UUID id);

    static void validateIfInitValueIsPositive(BigDecimal value) {
        if (validateIfNegativeValue(value)) {
            throw new NotEnoughMoneyException("You cannot set negative values for the account init balance!");
        }
    }
}
