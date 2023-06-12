package com.example.Preproject.service.email;

import com.example.Preproject.dto.PasswordResetDTO;
import org.springframework.http.ResponseEntity;

public interface PasswordResetService {
    boolean savePasswordReset(String email);
    String findTokenByUserId(Integer userId);
    boolean passwordResetUser(String token);
    void saveNewPassword(PasswordResetDTO passwordResetDTO);
    boolean hasEmail(String email);

}
