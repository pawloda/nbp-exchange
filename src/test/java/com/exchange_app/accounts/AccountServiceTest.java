package com.exchange_app.accounts;

import com.exchange_app.exceptions.AccountNotFoundException;
import com.exchange_app.exceptions.NotEnoughMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    private static final UUID ACCOUNT_ID = UUID.randomUUID();
    private static final Account ACCOUNT = new Account();
    private static final String FIRST_NAME = "John";
    private static final String SECOND_NAME = "Doe";
    private static final Double PLN = 999.99d;
    private static final Double USD = 999.99d;

    @BeforeEach
    void setAccount() {
        ACCOUNT.setId(ACCOUNT_ID);
        ACCOUNT.setFirstName(FIRST_NAME);
        ACCOUNT.setSecondName(SECOND_NAME);
        ACCOUNT.setPln(PLN);
        ACCOUNT.setUsd(USD);
    }

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountService service;

    @Test
    void whenCreateAccountNegativeDataShouldThrowExceptionTest() {
        //given
        ACCOUNT.setPln(-999.99d);

        //when
        assertThatExceptionOfType(NotEnoughMoneyException.class)
                .isThrownBy(() -> service.createAccount(ACCOUNT))
                .withMessage("You cannot set negative values for the account init balance!");

        //then:
        then(repository).shouldHaveNoInteractions();
    }

    @Test
    void whenCreateAccountDataShouldReturnUUIDTest() {
        //when
        var result = service.createAccount(ACCOUNT);

        //then:
        then(repository).should(times(1)).save(any());
        assertThat(result).isEqualTo(ACCOUNT_ID);
    }

    @Test
    void whenGetAccountNoDataShouldThrowExceptionTest() {
        //given:
        given(repository.findById(any(UUID.class))).willReturn(Optional.empty());

        //when
        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> service.getAccount(ACCOUNT_ID))
                .withMessage("The account with ID " + ACCOUNT_ID + " not found!");

        //then:
        then(repository).should(times(1)).findById(ACCOUNT_ID);
    }

    @Test
    void whenGetAccountShouldReturnEntityTest() {
        //given:
        var entity = new AccountEntity();
        entity.setId(ACCOUNT_ID);
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(entity));

        //when:
        var result = service.getAccount(ACCOUNT_ID);

        //then:
        then(repository).should(times(1)).findById(ACCOUNT_ID);
        assertThat(result.getId()).isEqualTo(ACCOUNT_ID);
    }
}