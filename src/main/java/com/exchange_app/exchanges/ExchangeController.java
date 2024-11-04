package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Exchange API", description = "Operation related to exchanges")
@RestController
@RequestMapping("/api/exchange")
class ExchangeController {
    private final ExchangeHandler handler;

    ExchangeController(final ExchangeHandler handler) {
        this.handler = handler;
    }

    @Operation(summary = "Exchange", description = "Exchange dollars to pln to usd and vice versa")
    @GetMapping("/{id}")
    ResponseEntity<Void> exchange(
            @Parameter(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @PathVariable UUID id,
            @Parameter(example = "USD") @NotNull @RequestParam String currency,
            @Parameter(example = "99.9") @NotNull @RequestParam String sum
    ) throws JsonProcessingException {
        handler.exchange(id, currency, sum);
        return ResponseEntity.noContent().build();
    }
}
