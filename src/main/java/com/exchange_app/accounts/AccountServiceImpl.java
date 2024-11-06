package com.exchange_app.accounts;

import com.exchange_app.exceptions.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
class AccountServiceImpl implements AccountService {
    private static final BigDecimal INITIAL_USD_VALUE = BigDecimal.ZERO;

    private final AccountRepository repository;

    AccountServiceImpl(final AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID createAccount(Account account) {
        AccountService.validateIfInitValueIsPositive(account.getPln());
        account.setUsd(INITIAL_USD_VALUE);
        var entity = AccountMapper.MAPPER.accountToEntity(account);
        return repository.save(entity).getId();
    }

    @Override
    public Account getAccount(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("The account with ID " + id + " not found!"));
        return AccountMapper.MAPPER.entityToAccount(entity);
    }
}
