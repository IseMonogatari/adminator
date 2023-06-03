package com.example.Preproject.controller.rest;

import com.example.Preproject.model.User;
import com.example.Preproject.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestRestController {

    @Autowired
    private RequestService requestService;

    // Создать заявку и отправить сообщение в ВК
    @PostMapping
    public ResponseEntity<String> changeRequestToTrueAndPostCommentInVK(@RequestParam("user_id") Integer userId,
                                                                       @RequestParam("status") boolean status) {
        requestService.postCommentAdnChangeRequest(userId, status);
        return new ResponseEntity<>("Выполнили POST запрос.", HttpStatus.OK);
    }

    // Получить всех пользователей с необработанными заявками
    @GetMapping
    public List<User> getUserWhichWantToBeANewAdmin() {
        return requestService.getUsersByRequest();
    }

    // Получить статус заявки для конкретного пользователя
    @GetMapping(params = "user_id")
    public boolean getUserActiveResponse(@RequestParam("user_id") Integer userId) {
        return requestService.getUserRequest(userId);
    }

    // Обновить статус заявки
    @PutMapping
    public ResponseEntity<String> changeUserRequest(@RequestParam("user_id") Integer userId,
                                                   @RequestParam("status") boolean status) {
        requestService.editRequest(userId, status);
        return new ResponseEntity<>("Статус пользователя с ID = " +
                userId + " был успешно изменён на " +
                status + ".", HttpStatus.OK);
    }
}
