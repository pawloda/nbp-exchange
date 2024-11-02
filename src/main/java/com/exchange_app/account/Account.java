package com.exchange_app.account;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Account {
    private UUID id;
    private String firstName;
    private String secondName;
    private Float plns;
    private Float dollars;
}
