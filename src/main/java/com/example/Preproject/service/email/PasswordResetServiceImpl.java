package com.example.Preproject.service.email;


import com.example.Preproject.dto.PasswordResetDTO;
import com.example.Preproject.model.PasswordReset;
import com.example.Preproject.model.User;
import com.example.Preproject.repository.PasswordResetRepository;
import com.example.Preproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${server.port}")
    private int port;

    private final String SUBJECT_RESET_PASSWORD = "Изменение пароля";


    @Override
    public void savePasswordReset(String email) {
        User user = usersRepository.findUserByEmail(email);
        passwordResetRepository.save(new PasswordReset(UUID.randomUUID().toString(), user));
        sendPasswordResetMessage(user);
    }

    @Override
    public String findTokenByUserId(Integer userId) {
        return passwordResetRepository.findPasswordResetById(userId).getToken();
    }

    @Override
    public boolean hasPaswordResetByToken(String token) {
        return passwordResetRepository.findByToken(token) != null;
    }

    @Override
    public boolean saveNewPassword(PasswordResetDTO passwordResetDTO) {
        if (isCorrectPasswordData(passwordResetDTO)) {
            PasswordReset passwordReset = passwordResetRepository.findByToken(passwordResetDTO.getToken());
            User user = passwordReset.getUser();
            user.setPassword(passwordEncoder.encode(passwordResetDTO.getPassword()));

            passwordResetRepository.delete(passwordReset);
            usersRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasEmail(String email) {
        return usersRepository.findUserByEmail(email) != null;
    }

    private void sendPasswordResetMessage(User user) {
        String message = String.format("Здравствуйте, %s!\n\n" +
                        "Для обновления пароля перейдите по ссылке: http://localhost:" + port +
                        "/forgot_passwords/new_password?token=%s",
                        user.getName(), findTokenByUserId(user.getId()));

        mailSender.send(user.getEmail(), SUBJECT_RESET_PASSWORD, message);
    }

    private boolean isCorrectPasswordData(PasswordResetDTO passwordResetDTO) {
        if (!StringUtils.hasText(passwordResetDTO.getPassword()) ||
                !passwordResetDTO.getPassword().equals(passwordResetDTO.getConfirmPassword())) {
            return false;
        } else {
            return true;
        }
    }
}
