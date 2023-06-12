package com.example.Preproject.service.email;


import com.example.Preproject.dto.PasswordResetDTO;
import com.example.Preproject.model.PasswordReset;
import com.example.Preproject.model.User;
import com.example.Preproject.repository.PasswordResetRepository;
import com.example.Preproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public boolean passwordResetUser(String token) {
        PasswordReset passwordReset = passwordResetRepository.findByToken(token);
        if (passwordReset == null) {
            return false;
        }
        passwordResetRepository.delete(passwordReset);
        return true;
    }

    @Override
    public boolean saveNewPassword(PasswordResetDTO passwordResetDTO) {
        User user = usersRepository.findUserByEmail(passwordResetDTO.getEmail());
        if (!StringUtils.hasText(passwordResetDTO.getPassword())) {
            return false;
        } else {
            if (!user.getEmail().equals(passwordResetDTO.getEmail())) {
                user.setEmail(passwordResetDTO.getEmail());
            }
            user.setPassword(passwordEncoder.encode(passwordResetDTO.getPassword()));
            usersRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean hasEmail(String email) {
        return usersRepository.findUserByEmail(email) != null;
    }

    private void sendPasswordResetMessage(User user) {
        String message = String.format("Здравствуйте, %s!\n\n" +
                        "Для обновления пароля перейдите по ссылке: http://localhost:" + port +
                        "/forgot_passwords/new_password/%s",
                        user.getName(), findTokenByUserId(user.getId()));

        mailSender.send(user.getEmail(), SUBJECT_RESET_PASSWORD, message);
    }
}
