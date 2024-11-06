package com.exchange_app.accounts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.exchange_app.accounts.AccountMapper.MAPPER;

@Component
class AccountHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountHandler.class);

    private final AccountServiceImpl service;

    AccountHandler(final AccountServiceImpl service) {
        this.service = service;
    }

    UUID createAccount(CreateAccountDto dto) {
        LOGGER.info("Create account request has been received for: {} {}", dto.firstName(), dto.secondName());
        var account = MAPPER.createDtoToAccount(dto);
        return service.createAccount(account);
    }

    ReadAccountDto getAccount(UUID id) {
        LOGGER.info("Get account request has been received for client with id: {}", id);
        return MAPPER.accountToReadDto(service.getAccount(id));
    }
}
