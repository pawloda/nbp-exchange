package com.exchange_app.exchanges;

import com.exchange_app.exceptions.AccountNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        var exchangeStrategy = switch (currencyToExchange) {
            case PLN -> new PlnToUsdExchangeStrategy(exchangeRateService);
            case USD -> new UsdToPlnExchangeStrategy(exchangeRateService);
        };
        plnAndUsd = exchangeStrategy.calculateExchange(plnAndUsd, sum);
        validateIfAccountBalanceAllowsOperation(id, plnAndUsd);
        repository.updateBalance(id, plnAndUsd.getFirst(), plnAndUsd.getSecond());
    }
}
