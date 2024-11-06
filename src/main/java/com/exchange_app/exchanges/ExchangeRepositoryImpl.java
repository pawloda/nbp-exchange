package com.exchange_app.exchanges;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
class ExchangeRepositoryImpl implements ExchangeRepository {
    private static final String ACCOUNT_ID = "id";
    private static final String PLN = "pln";
    private static final String USD = "usd";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExchangeRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Pair<BigDecimal, BigDecimal>> findPlnAndUsdById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT pln, usd FROM Account WHERE id = :id",
                    new MapSqlParameterSource(ACCOUNT_ID, id),
                    (rs, rowNum) -> Pair.of(rs.getBigDecimal(PLN), rs.getBigDecimal(USD)))
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateBalance(UUID id, BigDecimal pln, BigDecimal usd) {
        jdbcTemplate.update(
                "UPDATE Account SET pln = :pln, usd = :usd WHERE id = :id",
                new MapSqlParameterSource()
                        .addValue(PLN, pln)
                        .addValue(USD, usd)
                        .addValue(ACCOUNT_ID, id)
        );
    }
}
