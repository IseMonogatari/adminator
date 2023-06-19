package com.example.Preproject.service.email;

public interface MailSenderService {
    void send(String emailTo, String subject, String message);
}
