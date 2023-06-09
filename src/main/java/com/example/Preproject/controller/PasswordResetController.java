package com.example.Preproject.controller;

import com.example.Preproject.service.email.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/forgot_passwords")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping
    public String getPage() {
        return "forgotPassword";
    }

    @GetMapping("/new_password")
    public String getResetPage(@RequestParam("token") String token) {
        return passwordResetService.hasPasswordResetByToken(token) ? "newPassword" : "errorPage";
    }
}
