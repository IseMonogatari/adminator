package com.example.Preproject.service.email;

public interface MailSender {
    void send(String emailTo, String subject, String message);
}
