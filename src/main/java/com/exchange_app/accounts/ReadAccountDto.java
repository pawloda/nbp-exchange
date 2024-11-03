package com.exchange_app.accounts;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Read account dto")
class ReadAccountDto {
    @Schema(example = "John")
    public String firstName;

    @Schema(example = "Doe")
    public String secondName;

    @Schema(example = "999.99")
    public Float plns;

    @Schema(example = "999.99")
    public Float dollars;
}
