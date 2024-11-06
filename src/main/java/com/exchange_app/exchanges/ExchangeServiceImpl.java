package com.exchange_app.exchanges;

import com.exchange_app.exceptions.AccountNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.exchange_app.commons.CurrencyPairValidator.validateIfAccountBalanceAllowsOperation;

@Service
class ExchangeServiceImpl implements ExchangeService {
    private final ExchangeRepository repository;
    private final ExchangeRateService exchangeRateService;

    ExchangeServiceImpl(final ExchangeRepository repository, final ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
        this.repository = repository;
    }

    @Override
    public void exchange(UUID id, Currency currencyToExchange, BigDecimal sum) throws JsonProcessingException {
        var plnAndUsd = repository.findPlnAndUsdById(id)
                .orElseThrow(() -> new AccountNotFoundException("The account with ID " + id + " not found!"));
        plnAndUsd = switch (currencyToExchange) {
            case PLN -> calculateForPlnExchange(plnAndUsd, sum);
            case USD -> calculateForUsdExchange(plnAndUsd, sum);
        };
        validateIfAccountBalanceAllowsOperation(id, plnAndUsd);
        repository.updateBalance(id, plnAndUsd.getFirst(), plnAndUsd.getSecond());
    }

    private Pair<BigDecimal, BigDecimal> calculateForPlnExchange(Pair<BigDecimal, BigDecimal> plnAndUsd, BigDecimal sum)
            throws JsonProcessingException {
        var course = exchangeRateService.forPLNExchange();
        var pln = plnAndUsd.getFirst().subtract(sum);
        var usd = plnAndUsd.getSecond().add(sum.multiply(course));
        return Pair.of(pln, usd);
    }

    private Pair<BigDecimal, BigDecimal> calculateForUsdExchange(Pair<BigDecimal, BigDecimal> plnAndUsd, BigDecimal sum)
            throws JsonProcessingException {
        var course = exchangeRateService.forUSDExchange();
        var pln = plnAndUsd.getFirst().add(sum.multiply(course));
        var usd = plnAndUsd.getSecond().subtract(sum);
        return Pair.of(pln, usd);
    }
}
