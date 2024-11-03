//package com.exchange_app.exchanges;
//
//import com.exchange_app.accounts.AccountHandler;
//import com.exchange_app.accounts.CreateAccountDto;
//import com.exchange_app.accounts.ReadAccountDto;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@Tag(name = "Exchange API", description = "Operation related to exchanges")
//@RestController
//@RequestMapping("/api/exchange")
//class ExchangeController {
//    private final AccountHandler handler;
//
//    ExchangeController(final AccountHandler handler) {
//        this.handler = handler;
//    }
//
//    @Operation(summary = "Exchange", description = "Exchange dollars to plns or plns to dollars")
//    @GetMapping("/{id}")
//    ResponseEntity<ReadAccountDto> exchange(
//            @Parameter(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") @PathVariable UUID id,
//            @NotNull @RequestParam String currency) {
//        return ResponseEntity.ok(handler.getAccount(id));
//    }
//}
