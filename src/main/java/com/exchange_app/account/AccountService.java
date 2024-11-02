package com.exchange_app.account;

import org.springframework.stereotype.Service;

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

    Account getAccount(UUID id) {
        var optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("There is no account with id: " + id);
        }
        return MAPPER.entityToAccount(optional.get());
    }
}
