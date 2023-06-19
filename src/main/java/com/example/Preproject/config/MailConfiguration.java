package com.example.Preproject.config;

import com.example.Preproject.config.properties.MailConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Autowired
    private MailConfigProperties mailConfigProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailConfigProperties.getHost());
        mailSender.setPort(mailConfigProperties.getPort());
        mailSender.setUsername(mailConfigProperties.getUsername());
        mailSender.setPassword(mailConfigProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", mailConfigProperties.getProtocol());
        properties.setProperty("mail.smtp.auth", mailConfigProperties.getAuth());
        properties.setProperty("mail.smtp.starttls.enable", mailConfigProperties.getEnable());
        properties.setProperty("mail.debug", mailConfigProperties.getDebug());

        return  mailSender;
    }
}
