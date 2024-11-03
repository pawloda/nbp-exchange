package com.exchange_app.accounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Account API", description = "Operation related to accounts")
@RestController
@RequestMapping("/api/account")
class AccountController {
    private final AccountHandler handler;

    AccountController(final AccountHandler handler) {
        this.handler = handler;
    }

    @Operation(summary = "Create Account", description = "Create account from dto")
    @PostMapping
    ResponseEntity<UUID> createAccount(@Parameter(required = true) @Valid @RequestBody CreateAccountDto dto) {
        return ResponseEntity.ok(handler.createAccount(dto));
    }

    @Operation(summary = "Get Account", description = "Get account from database")
    @GetMapping("/{id}")
    ResponseEntity<ReadAccountDto> getAccount(@Parameter(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                                              @PathVariable UUID id) {
        return ResponseEntity.ok(handler.getAccount(id));
    }
}
