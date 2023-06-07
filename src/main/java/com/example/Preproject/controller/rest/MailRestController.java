package com.example.Preproject.controller.rest;

import com.example.Preproject.service.email.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
public class MailRestController {

    @Autowired
    private MailSender mailSender;

    //TODO Удалить контроллер!!!!

    @PostMapping
    public void sendMessageToGmail(@RequestParam("email_to") String  emailTo,
                                   @RequestParam("subject") String  subject,
                                   @RequestParam("message") String  message) {
        mailSender.send(emailTo, subject, message);
    }
}
