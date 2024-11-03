package com.exchange_app.account;

import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.exchange_app.account.AccountMapper.MAPPER;

@Service
class AccountService {
    private final AccountRepository repository;

    AccountService(final AccountRepository repository) {
        this.repository = repository;
    }

    UUID createAccount(Account account) {
        var entity = MAPPER.accountToEntity(account);
        return repository.save(entity).getId();
    }

    Account getAccount(UUID id) throws AccountNotFoundException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));
        return MAPPER.entityToAccount(entity);
    }
}
