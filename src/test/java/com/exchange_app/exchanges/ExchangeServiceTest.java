package com.exchange_app.exchanges;

import com.exchange_app.exceptions.AccountNotFoundException;
import com.exchange_app.exceptions.NotEnoughMoneyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {
    private static final UUID ACCOUNT_ID = UUID.randomUUID();
    private static final Double PLN_RESULT = 4.0425d;
    private static final Double USD_RESULT = 3.9625d;
    private static final Double SUM = 99.99d;

    @Mock
    GetCourse getCourse;

    @Mock
    ExchangeRepository repository;

    @InjectMocks
    ExchangeService service;

    @Test
    void whenExchangeAndNoRepoResponseShouldThrowExceptionTest() {
        //when
        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> service.exchange(ACCOUNT_ID, Currency.USD, SUM))
                .withMessage("The account with ID " + ACCOUNT_ID + " not found!");

        //then:
        then(repository).should(times(1)).findPlnAndUsdById(ACCOUNT_ID);
    }

    @ParameterizedTest
    @EnumSource(value = Currency.class)
    void whenExchangeAndNotEnoughBalanceShouldThrowExceptionTest(Currency value) throws JsonProcessingException {
        //give
        given(repository.findPlnAndUsdById(any(UUID.class))).willReturn(Pair.of(9.99d, 9.99d));
        if (value == Currency.PLN) given(getCourse.forPLNExchange()).willReturn(1 / PLN_RESULT);
        if (value == Currency.USD) given(getCourse.forUSDExchange()).willReturn(USD_RESULT);
        String currency = value == Currency.PLN ? "PLN" : "USD";

        //when
        assertThatExceptionOfType(NotEnoughMoneyException.class)
                .isThrownBy(() -> service.exchange(ACCOUNT_ID, value, SUM))
                .withMessage("The account with ID " + ACCOUNT_ID + " has not enough " + currency + "s!");

        //then:
        then(repository).should(times(1)).findPlnAndUsdById(ACCOUNT_ID);
    }

    private static Object[][] provideCurrenciesAndResults() {
        return new Object[][] {
                { Currency.PLN, 1 / PLN_RESULT, 9900.0d, 10024.724693877552d },
                { Currency.USD, USD_RESULT, 10396.200375d, 9900.0d }
        };
    }

    @ParameterizedTest
    @MethodSource("provideCurrenciesAndResults")
    void whenExchangeShouldInvokeRepoWithProperValuesTest(Currency value, Double course, Double plnResult,
                                                          Double usdResult) throws JsonProcessingException {
        //give
        given(repository.findPlnAndUsdById(any(UUID.class))).willReturn(Pair.of(9999.99d, 9999.99d));
        if (value == Currency.PLN) given(getCourse.forPLNExchange()).willReturn(course);
        if (value == Currency.USD) given(getCourse.forUSDExchange()).willReturn(course);
        var plnCaptor = forClass(Double.class);
        var usdCaptor = forClass(Double.class);

        //when
        service.exchange(ACCOUNT_ID, value, SUM);

        //then:
        then(repository).should(times(1))
                .updateBalance(eq(ACCOUNT_ID), plnCaptor.capture(), usdCaptor.capture());
        assertThat(plnCaptor.getValue()).isCloseTo(plnResult, Offset.offset(0.01d));
        assertThat(usdCaptor.getValue()).isCloseTo(usdResult, Offset.offset(0.01d));
    }
}
