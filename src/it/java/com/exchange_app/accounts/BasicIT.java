package com.exchange_app.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BasicIT {
    protected static final String FIRST_NAME = "John";
    protected static final String SECOND_NAME = "Doe";
    protected static final Double PLN = 999.99d;

    protected UUID id;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void sendPostAndInitId() throws Exception {
        var response = mockMvc.perform(post("/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccount())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
        var idResponse = response.andReturn().getResponse().getContentAsString();
        id = UUID.fromString(idResponse.substring(1, idResponse.length() - 1));
    }

    private CreateAccountDto createAccount() {
        CreateAccountDto dto = new CreateAccountDto();
        dto.setFirstName(FIRST_NAME);
        dto.setSecondName(SECOND_NAME);
        dto.setPln(PLN);
        return dto;
    }
}
