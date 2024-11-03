package com.exchange_app.accounts;

import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.exchange_app.commons.CurrencyPairValidator.validateIfInitValueIsPositive;
import static com.exchange_app.exceptions.ExceptionFactory.throwAccountNotFoundException;

@Service
class AccountService {
    private static final double INITIAL_USD_VALUE = 0.0d;

    private final AccountRepository repository;

    AccountService(final AccountRepository repository) {
        this.repository = repository;
    }

    UUID createAccount(Account account) {
        validateIfInitValueIsPositive(account.getPln());
        account.setUsd(INITIAL_USD_VALUE);
        var entity = AccountMapper.MAPPER.accountToEntity(account);
        return repository.save(entity).getId();
    }

    Account getAccount(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> throwAccountNotFoundException(id));
        return AccountMapper.MAPPER.entityToAccount(entity);
    }
}
