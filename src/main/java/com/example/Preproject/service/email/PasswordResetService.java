package com.example.Preproject.service.email;

import com.example.Preproject.dto.PasswordResetDTO;

public interface PasswordResetService {
    void savePasswordReset(String email);
    String findTokenByUserId(Integer userId);
    boolean hasPasswordResetByToken(String token);
    boolean saveNewPassword(PasswordResetDTO passwordResetDTO);
    boolean hasEmail(String email);

}
