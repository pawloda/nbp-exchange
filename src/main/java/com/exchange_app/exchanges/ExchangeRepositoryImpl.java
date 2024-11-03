package com.exchange_app.exchanges;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
class ExchangeRepositoryImpl implements ExchangeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ExchangeRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Pair<Double, Double> findPlnAndUsdById(UUID id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT pln, usd FROM Account WHERE id = :id",
                    new MapSqlParameterSource("id", id),
                    (rs, rowNum) -> Pair.of(rs.getDouble("pln"), rs.getDouble("usd"))
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateBalance(UUID id, Double pln, Double usd) {
        jdbcTemplate.update(
                "UPDATE Account SET pln = :pln, usd = :usd WHERE id = :id",
                new MapSqlParameterSource()
                        .addValue("pln", pln)
                        .addValue("usd", usd)
                        .addValue("id", id)
        );
    }
}
