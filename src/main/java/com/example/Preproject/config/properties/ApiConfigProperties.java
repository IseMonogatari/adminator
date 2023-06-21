package com.example.Preproject.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
@ConfigurationProperties(prefix = "api")
public class ApiConfigProperties {

    private int port;
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
