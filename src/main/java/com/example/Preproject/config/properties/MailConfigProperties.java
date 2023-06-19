package com.example.Preproject.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@org.springframework.boot.context.properties.ConfigurationProperties(prefix = "spring.mail")
public class MailConfigProperties {
    private String host;
    private String username;
    private String password;
    private int port;
    private String protocol;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String enable;
    private String debug;
}
