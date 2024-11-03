package com.exchange_app.account;

import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.exchange_app.account.AccountMapper.MAPPER;

@Component
class AccountHandler {
    private final AccountService service;

    AccountHandler(final AccountService service) {
        this.service = service;
    }

    UUID createAccount(CreateAccountDto dto) {
        return service.createAccount(MAPPER.createDtoToAccount(dto));
    }

    ReadAccountDto getAccount(UUID id) throws AccountNotFoundException {
        return MAPPER.accountToReadDto(service.getAccount(id));
    }
}
