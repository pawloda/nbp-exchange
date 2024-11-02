package com.exchange_app.account;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@Tag(name = "test")
class AccountController {
    private final AccountHandler handler;

    AccountController(final AccountHandler handler) {
        this.handler = handler;
    }

    @PostMapping
    UUID createAccount(@Valid @RequestBody CreateAccountDto dto) {
        return handler.createAccount(dto);
    }

    @GetMapping("/{id}")
    ReadAccountDto getAccount(@PathVariable UUID id) {
        return handler.getAccount(id);
    }
}
