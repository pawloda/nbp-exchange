package com.exchange_app.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
class AccountEntity {
    @Id
    private UUID id;
    private String firstName;
    private String secondName;
    private Float plns;
    private Float dollars;

    UUID getId() {
        return id;
    }
}
