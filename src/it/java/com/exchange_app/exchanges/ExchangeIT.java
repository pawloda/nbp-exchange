package com.exchange_app.exchanges;

import com.exchange_app.accounts.BasicIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeIT extends BasicIT {
    @Test
    void createAccountAndGetAfterExchange_ShouldReturnAccount() throws Exception {
        //given
        String sum = "99.99";

        //when
        mockMvc.perform(get("/api/exchange/{id}", id)
                        .param("currency", "PLN")
                        .param("sum", sum)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //then:
        mockMvc.perform(get("/api/account/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$.secondName").value(SECOND_NAME))
                .andExpect(jsonPath("$.pln").value(PLN - Double.parseDouble(sum)))
                .andExpect(jsonPath("$.usd").value(greaterThan(20.0)))
                .andExpect(jsonPath("$.usd").value(lessThan(30.0)));
    }
}
