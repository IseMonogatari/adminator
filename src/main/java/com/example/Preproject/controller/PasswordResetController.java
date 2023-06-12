package com.example.Preproject.controller;

import com.example.Preproject.service.email.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/new_password/{token}")
    public String getResetPage(Model model, @PathVariable String token) {
        boolean isToken = passwordResetService.passwordResetUser(token);
        if (isToken) {
            model.addAttribute("message", "Введите новый пароль.");
        } else {
            //TODO Надо подумать, как тогда запретить передачу данных
            model.addAttribute("message", "Записи токена почему-то нет...");
        }
        return "newPassword";
    }
}
