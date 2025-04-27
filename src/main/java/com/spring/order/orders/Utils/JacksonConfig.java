package com.spring.order.orders.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Register the JavaTimeModule for Java 8 Date/Time types
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // Disable writing dates as timestamps
        return objectMapper;
    }
}
