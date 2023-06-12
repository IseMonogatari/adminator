package com.example.Preproject.service.email;

import com.example.Preproject.dto.PasswordResetDTO;
import org.springframework.http.ResponseEntity;

public interface PasswordResetService {
    void savePasswordReset(String email);
    String findTokenByUserId(Integer userId);
    boolean passwordResetUser(String token);
    boolean saveNewPassword(PasswordResetDTO passwordResetDTO);
    boolean hasEmail(String email);

}
