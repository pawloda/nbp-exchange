package com.exchange_app.account;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Account API")
class AccountController {
    private final AccountHandler handler;

    AccountController(final AccountHandler handler) {
        this.handler = handler;
    }

    @PostMapping
    ResponseEntity<UUID> createAccount(@Valid @RequestBody CreateAccountDto dto) {
        return ResponseEntity.ok(handler.createAccount(dto));
    }

    @GetMapping("/{id}")
    ResponseEntity<ReadAccountDto> getAccount(@PathVariable UUID id) throws AccountNotFoundException {
        return ResponseEntity.ok(handler.getAccount(id));
    }
}
