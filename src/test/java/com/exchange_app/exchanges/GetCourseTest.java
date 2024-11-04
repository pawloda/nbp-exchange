package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class GetCourseTest {
    private static final String NBP_INVALID_RESPONSE = "INVALID";
    private static final String NBP_RESPONSE = "";
    private static final Double PLN = 999.99d;
    private static final Double USD = 999.99d;

    @Mock
    RestTemplate template;

    @InjectMocks
    GetCourse getCourse;

    @ParameterizedTest
    @ValueSource(strings = { "PLN", "USD" })
    void whenForPlnExchangeThereIsNoNbpResponseShouldThrowExceptionTest(String currency) {
        //given
        given(template.getForObject(anyString(), any())).willReturn(NBP_INVALID_RESPONSE);
        
        //when
        assertThatExceptionOfType(JsonProcessingException.class).isThrownBy(
                "PLN".equals(currency) ? () -> getCourse.forPLNExchange() : () -> getCourse.forUSDExchange()
        );

        //then:
        then(template).should(times(1)).getForObject(anyString(), any());
    }
}
