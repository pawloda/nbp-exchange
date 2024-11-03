package com.exchange_app.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ReadAccountDto {
    @Schema(name = "First Name", example = "John")
    private String firstName;

    @Schema(name = "Second Name", example = "Doe")
    private String secondName;

    @Schema(name = "Balance in PLN", example = "999,99")
    private Float plns;

    @Schema(name = "Balance in Dollars", example = "999,99")
    private Float dollars;
}
