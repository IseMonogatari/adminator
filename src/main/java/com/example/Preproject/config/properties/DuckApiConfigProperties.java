package com.example.Preproject.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "api.duck")
public class DuckApiConfigProperties {
    private String URL_DUCK_API;
    private String URL_FOR_RANDOM_DUCK;
}
