package com.exchange_app.accounts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Read account dto")
@Getter
@Setter
class ReadAccountDto {
    @Schema(example = "John")
    private String firstName;

    @Schema(example = "Doe")
    private String secondName;

    @Schema(example = "999.99")
    private Double pln;

    @Schema(example = "999.99")
    private Double usd;
}
