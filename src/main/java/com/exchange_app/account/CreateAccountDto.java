package com.exchange_app.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class CreateAccountDto {
    @Schema(name = "First Name", example = "John")
    @Size(min = 1, message = "The size must be greater than 1!")
    private String firstName;

    @Schema(name = "Second Name", example = "Doe")
    @Size(min = 1, message = "The size must be greater than 1!")
    private String secondName;

    @Schema(name = "Balance in PLN", example = "999,99")
    @Min(value = 1, message = "The balance must be greater than 1,00")
    private Float plns;
}
