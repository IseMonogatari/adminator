package com.example.Preproject.service;


import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.model.Role;
import com.example.Preproject.model.User;
import com.example.Preproject.repository.RolesRepository;
import com.example.Preproject.repository.UsersRepository;
import com.example.Preproject.service.API.DuckService;
import com.example.Preproject.util.FormatterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private DuckService duckService;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = usersRepository.findByName(userDTO.getName());
        if (user != null && user.getRoles().contains(roleService.adminRole())) {
            user.getRoles().add(roleService.getRoleByName(userDTO.getRole()));
        } else if (user != null && user.getRoles().contains(roleService.userRole())) {
            return null;
        } else if (usersRepository.findUserByEmail(userDTO.getEmail()) != null) {
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
        duckService.saveDuck(user);
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
        return new UserDTO(user, userAge(user), duckService.findDuckURLFromDB(user));
    }

    @Override
    public User findUserByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }

    @Override
    public List<UserDTO> allUsers() {
        return usersRepository.findAll()
                .stream()
                .map(u -> new UserDTO(u, userAge(u), duckService.findDuckURLFromDB(u)))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User updatedUser = usersRepository.findUserById(Integer.valueOf(userDTO.getId()));
//        updatedUser = checkUserRole(updatedUser, userDTO, "ROLE_ADMIN", "ROLE_USER");
        updatedUser = checkUserEntity(updatedUser, userDTO);
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


    private User checkUserRole(User user, UserDTO userDTO, String fistRole, String secondRole) {
        if (user.getRoles().contains(rolesRepository.findByRole(fistRole))) {
            user.setLastName(userDTO.getLastName());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setBirthday(userBirthday(userDTO));
            checkChangePassword(user, userDTO);
            checkUserRolesForRolePresence(user, userDTO, fistRole);
        } else if (equalsUserDataWithoutRole(user, userDTO)) {  //условие для добавления роли АДМИН через кнопку
            user.getRoles().add(rolesRepository.findByRole(userDTO.getRole()));
        } else if (user.getRoles().contains(rolesRepository.findByRole(secondRole))
                && (checkUserRolesForRolePresence(user, userDTO, fistRole) == null)) {
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

    private User checkUserRolesForRolePresence(User user, UserDTO userDTO, String role) {
        if (!Objects.equals(userDTO.getRole(), role)) {
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
        return Period.between(user.getBirthday(), LocalDate.now()).getYears();
    }

    @Autowired
    private RoleService roleService;

    private User checkUserEntity(User user, UserDTO userDTO) {
        if (user.getRoles().contains(roleService.adminRole())) {
            user.setLastName(userDTO.getLastName());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setBirthday(userBirthday(userDTO));
            checkChangePassword(user, userDTO);
            checkForRole(user, userDTO, roleService.adminRole());
        } else if (equalsUserDataWithoutRole(user, userDTO)) {  //условие для добавления роли АДМИН через кнопку
            user.getRoles().add(roleService.getRoleByName(userDTO.getRole()));
        } else if (user.getRoles().contains(roleService.userRole())
                && (checkForRole(user, userDTO, roleService.adminRole()) == null)) {
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

    private User checkForRole(User user, UserDTO userDTO, Role role) {
        if (new HashSet<>(userDTORoles(userDTO)).containsAll(roleService.namesOfAllRoles())
                || Objects.equals(userDTO.getRole(), role.getRole())) {
            return null;
        } else {
            user.getRoles().add(roleService.getRoleByName(userDTO.getRole()));
            return user;
        }
    }

    private List<String> userDTORoles(UserDTO userDTO) {
        return Arrays.stream(userDTO.getRole().split("\\s"))
                .collect(Collectors.toList());
    }
}