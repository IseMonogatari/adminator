package com.example.Preproject.repository;

import com.example.Preproject.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Integer> {
    PasswordReset findPasswordResetById(Integer userId);
    PasswordReset findByToken(String token);
}
