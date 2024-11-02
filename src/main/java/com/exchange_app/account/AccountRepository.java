package com.exchange_app.account;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.UUID;

interface AccountRepository extends JpaRepositoryImplementation<AccountEntity, UUID> {
    //Interface repository
}
