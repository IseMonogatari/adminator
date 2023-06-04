package com.example.Preproject.service;

import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserDTO userRegistrationDTO);
    UserDTO findUserById(Integer id);
    List<UserDTO> allUsers();
    UserDTO update(UserDTO userRegistrationDTO);
    boolean deleteUser(Integer id);
}
