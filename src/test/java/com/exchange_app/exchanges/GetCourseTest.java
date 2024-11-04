package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class GetCourseTest {
    private static final Double PLN_RESULT = 4.0425d;
    private static final Double USD_RESULT = 3.9625d;
    private static final String PLN = "PLN";
    private static final String USD = "USD";
    private static final String NBP_INVALID_RESPONSE = "INVALID";
    private static final String NBP_RESPONSE = "{\"table\":\"C\",\"code\":\"USD\",\"rates\":[" +
            "{\"effectiveDate\":\"2024-10-30\",\"bid\":3.9808,\"ask\":4.0612}," +
            "{\"effectiveDate\":\"2024-11-04\",\"bid\":" + USD_RESULT + ",\"ask\":" + PLN_RESULT + "}" +
            "]}";

    @Mock
    RestTemplate template;

    @InjectMocks
    GetCourse getCourse;

    @ParameterizedTest
    @ValueSource(strings = { PLN, USD })
    void whenForPlnExchangeThereIsNoNbpResponseShouldThrowExceptionTest(String currency) {
        //given
        given(template.getForObject(anyString(), any())).willReturn(NBP_INVALID_RESPONSE);
        
        //when
        assertThatExceptionOfType(JsonProcessingException.class).isThrownBy(
                PLN.equals(currency) ? () -> getCourse.forPLNExchange() : () -> getCourse.forUSDExchange()
        );

        //then:
        then(template).should(times(1)).getForObject(anyString(), any());
    }

    private static Object[][] provideCurrenciesAndResults() {
        return new Object[][] {
                { PLN, 1 / PLN_RESULT },
                { USD, USD_RESULT }
        };
    }

    @ParameterizedTest
    @MethodSource("provideCurrenciesAndResults")
    void whenForPlnExchangeThereIsNbpResponseShouldReturnCourseTest(String currency, Double expected)
            throws JsonProcessingException {
        //given
        given(template.getForObject(anyString(), any())).willReturn(NBP_RESPONSE);

        //when
        var result = PLN.equals(currency) ? getCourse.forPLNExchange() : getCourse.forUSDExchange();

        //then:
        then(template).should(times(1)).getForObject(anyString(), any());
        assertThat(result).isEqualTo(expected);
    }
}
