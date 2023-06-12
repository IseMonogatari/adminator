package com.example.Preproject.controller.rest;

import com.example.Preproject.dto.PasswordResetDTO;
import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.service.email.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset_passwords")
public class PasswordResetRestController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping
    public ResponseEntity<String> sendMail(@RequestBody PasswordResetDTO passwordResetDTO) {
        if (passwordResetService.hasEmail(passwordResetDTO.getEmail())) {
            passwordResetService.savePasswordReset(passwordResetDTO.getEmail());
            return new ResponseEntity<>("Сохранение и отправка произошли", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("У пользователя нет почты", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<String> newPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        if (passwordResetService.saveNewPassword(passwordResetDTO)) {
            return new ResponseEntity<>("Успешное обновление пароля!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Вы не передали никакого пароля!", HttpStatus.BAD_REQUEST);
        }
    }
}
