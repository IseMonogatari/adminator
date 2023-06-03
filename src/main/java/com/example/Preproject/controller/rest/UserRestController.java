package com.example.Preproject.controller.rest;


import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.model.User;
import com.example.Preproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    // Получить список всех пользователей
    @GetMapping
    public List<User> getUserList() {
        return userService.allUsers();
    }

    // Получить авторизованного пользователя
    @GetMapping("/authorized")
    public User getAuthorizedUser(@AuthenticationPrincipal User user) {
        return user;
    }

    // Получить пользователя по id
    @GetMapping(params = "id")
    public User getUserById(@RequestParam("id") Integer id) {
        return userService.findUserById(id);
    }

    // Сохраняем пользователя
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserDTO userRegistrationDTO) {
        if (userService.save(userRegistrationDTO) != null) {
            return new ResponseEntity<>("Пользователь добавлен", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Данный пользователь не может быть админом",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Обновляем пользователя
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userRegistrationDTO) {
        if (userService.update(userRegistrationDTO) != null) {
            return new ResponseEntity<>("Пользователь обновлён", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Данный пользователь не может быть админом",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Удалить пользователяпо id
    @DeleteMapping
    public void deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
    }
}
