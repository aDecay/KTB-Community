package com.chad.community.config;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {
    @Bean
    JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }
}