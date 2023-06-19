package com.example.Preproject.config.properties;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Data
public class TemplateConfigProperties {
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
