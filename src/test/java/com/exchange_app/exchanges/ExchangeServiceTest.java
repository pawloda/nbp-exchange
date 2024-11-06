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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
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
    private static final BigDecimal PLN_RESULT = BigDecimal.valueOf(4.0425d);
    private static final BigDecimal USD_RESULT = BigDecimal.valueOf(3.9625d);
    private static final BigDecimal SUM = BigDecimal.valueOf(99.99d);
    private static final Integer SCALE = 10;

    @Mock
    ExchangeRateService exchangeRateService;

    @Mock
    ExchangeRepository repository;

    @InjectMocks
    ExchangeServiceImpl service;

    @Test
    void whenExchangeAndNoRepoResponse_ShouldThrowExceptionTest() {
        //when
        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> service.exchange(ACCOUNT_ID, Currency.USD, SUM))
                .withMessage("The account with ID " + ACCOUNT_ID + " not found!");

        //then:
        then(repository).should(times(1)).findPlnAndUsdById(ACCOUNT_ID);
    }

    @ParameterizedTest
    @EnumSource(value = Currency.class)
    void whenExchangeAndNotEnoughBalance_ShouldThrowExceptionTest(Currency value) throws JsonProcessingException {
        //give
        given(repository.findPlnAndUsdById(any(UUID.class)))
                .willReturn(Optional.of(Pair.of(BigDecimal.valueOf(9.99d), BigDecimal.valueOf(9.99d))));
        if (value == Currency.PLN) given(exchangeRateService.forPLNExchange())
                .willReturn(BigDecimal.ONE.divide(PLN_RESULT, SCALE, RoundingMode.HALF_UP));
        if (value == Currency.USD) given(exchangeRateService.forUSDExchange()).willReturn(USD_RESULT);
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
                { Currency.PLN, BigDecimal.ONE.divide(PLN_RESULT, SCALE, RoundingMode.HALF_UP),
                        BigDecimal.valueOf(9900.0d), BigDecimal.valueOf(10024.724693877552d) },
                { Currency.USD, USD_RESULT, BigDecimal.valueOf(10396.200375d), BigDecimal.valueOf(9900.0d) }
        };
    }

    @ParameterizedTest
    @MethodSource("provideCurrenciesAndResults")
    void whenExchange_ShouldInvokeRepoWithProperValuesTest(Currency value, BigDecimal course, BigDecimal plnResult,
                                                           BigDecimal usdResult) throws JsonProcessingException {
        //give
        given(repository.findPlnAndUsdById(any(UUID.class)))
                .willReturn(Optional.of(Pair.of(BigDecimal.valueOf(9999.99d), BigDecimal.valueOf(9999.99d))));
        if (value == Currency.PLN) given(exchangeRateService.forPLNExchange()).willReturn(course);
        if (value == Currency.USD) given(exchangeRateService.forUSDExchange()).willReturn(course);
        var plnCaptor = forClass(BigDecimal.class);
        var usdCaptor = forClass(BigDecimal.class);

        //when
        service.exchange(ACCOUNT_ID, value, SUM);

        //then:
        then(repository).should(times(1))
                .updateBalance(eq(ACCOUNT_ID), plnCaptor.capture(), usdCaptor.capture());
        assertThat(plnCaptor.getValue().doubleValue()).isCloseTo(plnResult.doubleValue(), Offset.offset(0.01d));
        assertThat(usdCaptor.getValue().doubleValue()).isCloseTo(usdResult.doubleValue(), Offset.offset(0.01d));
    }
}
