package com.example.Preproject.service;


import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.model.User;
import com.example.Preproject.repository.RolesRepository;
import com.example.Preproject.repository.UsersRepository;
import com.example.Preproject.util.FormatterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    LocalDate nowTime = LocalDate.now();


    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = usersRepository.findByName(userDTO.getName());
        if (user != null && user.getRoles().contains(rolesRepository.findByRole("ROLE_ADMIN"))) {
            user.getRoles().add(rolesRepository.findByRole(userDTO.getRole()));
        } else if (user != null && user.getRoles().contains(rolesRepository.findByRole("ROLE_USER"))) {
            return null;
        } else {
            user = new User();
            user.setLastName(userDTO.getLastName());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setBirthday(userBirthday(userDTO));
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRoles(Collections.singleton(rolesRepository.findByRole(userDTO.getRole())));
        }
        usersRepository.save(user);
        return new UserDTO(user, userAge(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Неверный логин или пароль.");
        }
        return user;
    }

    @Override
    public UserDTO findUserById(Integer id) {
        User user = usersRepository.findUserById(id);
        return new UserDTO(user, userAge(user));
    }

    @Override
    public User findUserByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }

    @Override
    public List<UserDTO> allUsers() {
        return usersRepository.findAll()
                .stream()
                .map(u -> new UserDTO(u, userAge(u)))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User updatedUser = usersRepository.findUserById(Integer.valueOf(userDTO.getId()));
        updatedUser = checkRoleData(updatedUser, userDTO, "ROLE_ADMIN", "ROLE_USER");
        if (updatedUser == null) {
            return null;
        }
        usersRepository.save(updatedUser);
        return new UserDTO(updatedUser, userAge(updatedUser));
    }

    @Override
    public boolean deleteUser(Integer id) {
        if (usersRepository.findById(id).isPresent()) {
            usersRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // ---- Приватные методы ----


    private User checkRoleData(User user, UserDTO userDTO, String fistRole, String secondRole) {
        if (user.getRoles().contains(rolesRepository.findByRole(fistRole))) {
            user.setLastName(userDTO.getLastName());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setBirthday(userBirthday(userDTO));
            checkChangePassword(user, userDTO);
            checkEqualRole(user, userDTO, fistRole);
        } else if (equalsUserDataWithoutRole(user, userDTO)) {  //условие для добавления роли АДМИН через кнопку
            user.getRoles().add(rolesRepository.findByRole(userDTO.getRole()));
        } else if (user.getRoles().contains(rolesRepository.findByRole(secondRole))
                && (checkEqualRole(user, userDTO, fistRole) == null)) {
            return null;
        } else {
            user.setLastName(userDTO.getLastName());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setBirthday(userBirthday(userDTO));
            checkChangePassword(user, userDTO);
        }
        return user;
    }

    private void checkChangePassword(User user, UserDTO userDTO) {
        if (!userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
    }

    private User checkEqualRole(User user, UserDTO userDTO, String ROLE) {
        if (!Objects.equals(userDTO.getRole(), ROLE)) {
            user.getRoles().add(rolesRepository.findByRole(userDTO.getRole()));
            return user;
        }
        return null;
    }

    private boolean equalsUserDataWithoutRole(User user, UserDTO userDTO) {
        return userDTO.toStringWithoutPassAndRole().equals(user.toStringWithoutPassAndRole())
                && userDTO.getPassword() == null;
    }

    private LocalDate userBirthday(UserDTO userDTO) {
        return LocalDate.parse(userDTO.getBirthday(), FormatterUtils.defaultDateFormatter());
    }

    private Integer userAge(User user) {
        return Period.between(user.getBirthday(), nowTime).getYears();
    }
}