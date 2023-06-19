package com.example.Preproject.config.properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "api.tokens")
public class ApiConfigProperties {
    private String vkWithBearer;
    private String vkWithoutBearer;
}
