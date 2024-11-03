package com.exchange_app.accounts;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface AccountMapper {
    AccountMapper MAPPER = Mappers.getMapper(AccountMapper.class);

    AccountEntity accountToEntity(Account account);

    Account entityToAccount(AccountEntity entity);

    Account createDtoToAccount(CreateAccountDto dto);

    ReadAccountDto accountToReadDto(Account account);
}
