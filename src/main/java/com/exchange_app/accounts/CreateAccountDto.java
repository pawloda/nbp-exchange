package com.exchange_app.accounts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = "Create account dto")
@Getter
@Setter
class CreateAccountDto {
    @Schema(example = "John")
    @NotBlank(message = "The size must be greater than 1!")
    private String firstName;

    @Schema(example = "Doe")
    @NotBlank(message = "The size must be greater than 1!")
    private String secondName;

    @Schema(example = "999.99")
    @Min(value = 1, message = "The balance must be greater than 1.00")
    private BigDecimal pln;
}
