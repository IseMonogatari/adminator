package com.example.Preproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordResetDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String token;
}
