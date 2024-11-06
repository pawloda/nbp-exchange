package com.exchange_app.accounts;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Read account dto")
public record ReadAccountDto(
        @Schema(example = "John") String firstName,
        @Schema(example = "Doe") String secondName,
        @Schema(example = "999.99") BigDecimal pln,
        @Schema(example = "999.99") BigDecimal usd
) {
    //Record with no logic
}
