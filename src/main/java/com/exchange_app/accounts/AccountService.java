package com.exchange_app.accounts;

import com.exchange_app.exceptions.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class AccountService {
    private final AccountRepository repository;

    AccountService(final AccountRepository repository) {
        this.repository = repository;
    }

    UUID createAccount(Account account) {
        account.setDollars(0.0F);
        var entity = AccountMapper.MAPPER.accountToEntity(account);
        return repository.save(entity).getId();
    }

    Account getAccount(UUID id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));
        return AccountMapper.MAPPER.entityToAccount(entity);
    }
}
