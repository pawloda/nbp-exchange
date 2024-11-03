package com.exchange_app.accounts;

import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.exchange_app.accounts.AccountMapper.MAPPER;

@Component
class AccountHandler {
    private final AccountService service;

    AccountHandler(final AccountService service) {
        this.service = service;
    }

    UUID createAccount(CreateAccountDto dto) {
        var account = MAPPER.createDtoToAccount(dto);
        return service.createAccount(account);
    }

    ReadAccountDto getAccount(UUID id) {
        return MAPPER.accountToReadDto(service.getAccount(id));
    }
}
