package com.exchange_app.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Currency Exchange API",
                version = "1.0",
                description = "API for exchanging USD and PLN"
        )
)
class OpenApiConfig {
    //Needed to set info
}
