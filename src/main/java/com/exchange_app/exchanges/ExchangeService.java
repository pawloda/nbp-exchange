package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.exchange_app.commons.CurrencyPairValidator.validateIfAccountBalanceAllowsOperation;
import static com.exchange_app.commons.CurrencyPairValidator.validateIfAccountBalanceExists;

@Service
class ExchangeService {
    private final ExchangeRepositoryImpl repository;
    private final GetCourse getCourse;

    ExchangeService(final ExchangeRepositoryImpl repository, final GetCourse getCourse) {
        this.getCourse = getCourse;
        this.repository = repository;
    }

    //I could use strategy here, so I wanted to implement request once for both - but it could be next step (taking
    // values for 2 currencies and then for every pair implement a class calculateFor1curr2curr)
    void exchange(UUID id, Currency currencyToExchange, Double sum) throws JsonProcessingException {
        var plnAndUsd = repository.findPlnAndUsdById(id);
        validateIfAccountBalanceExists(id, plnAndUsd);
        if(currencyToExchange == Currency.PLN) {
            plnAndUsd = calculateForPlnExchange(plnAndUsd, sum);
        } else {
            plnAndUsd = calculateForUsdExchange(plnAndUsd, sum);
        }
        validateIfAccountBalanceAllowsOperation(id, plnAndUsd);
        repository.updateBalance(id, plnAndUsd.getFirst(), plnAndUsd.getSecond());
    }

    private Pair<Double, Double> calculateForPlnExchange(Pair<Double, Double> plnAndUsd, Double sum)
            throws JsonProcessingException {
        var course = getCourse.forPLNExchange();
        var pln = plnAndUsd.getFirst() - sum;
        var usd = plnAndUsd.getSecond() + sum * course;
        return Pair.of(pln, usd);
    }

    private Pair<Double, Double> calculateForUsdExchange(Pair<Double, Double> plnAndUsd, Double sum)
            throws JsonProcessingException {
        var course = getCourse.forUSDExchange();
        var pln = plnAndUsd.getFirst() + sum * course;
        var usd = plnAndUsd.getSecond() - sum;
        return Pair.of(pln, usd);
    }
}
