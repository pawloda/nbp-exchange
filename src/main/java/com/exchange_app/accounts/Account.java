package com.exchange_app.accounts;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
class Account {
    private UUID id;
    private String firstName;
    private String secondName;
    private Double pln;
    private Double usd;
}
