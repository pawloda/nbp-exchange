package com.exchange_app.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountIT extends BasicIT {
    @Test
    void createAccountAndGet_ShouldReturnAccount() throws Exception {
        //then:
        mockMvc.perform(get("/api/account/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.secondName").value(SECOND_NAME))
                .andExpect(jsonPath("$.pln").value(PLN))
                .andExpect(jsonPath("$.usd").value(0d));
    }
}

