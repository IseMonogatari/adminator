package com.example.Preproject.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "api.vk")
public class VkApiConfigProperties {
    private String tokenWithBearer;
    private String tokenWithoutBearer;
    private String URL_CREATE_COMMENT;
    private String URL_GET_COMMENTS;
    private String URL_UPDATE_COMMENT;
    private String URL_DELETE_COMMENT;
    private String OWNER_ID;
    private String POST_ID;
    private String PREVIEW_LENGTH;
    private String V;
}
