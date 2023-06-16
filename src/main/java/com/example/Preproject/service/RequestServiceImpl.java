package com.example.Preproject.service;

import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.model.Request;
import com.example.Preproject.model.User;
import com.example.Preproject.repository.RequestRepository;
import com.example.Preproject.repository.UsersRepository;
import com.example.Preproject.service.API.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private VkService vkService;

    @Override
    public List<UserDTO> getUsersByRequest() {
        return requestRepository.findAllByActiveRequest(true)
                .stream()
                .map(Request::getUser)
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void saveRequest(Integer userId, boolean status) {
        if (requestRepository.findRequestById(userId) != null) {
            editRequest(userId, true);
        } else {
            requestRepository.save(new Request(status, usersRepository.findUserById(userId)));
        }
    }

    @Override
    public void editRequest(Integer userId, boolean status) {
        Request editRequest = requestRepository.findRequestById(userId);
        editRequest.setActiveRequest(status);
        requestRepository.save(editRequest);
    }

    @Override
    public void postCommentAdnChangeRequest(Integer userId, boolean status) {
        vkService.sendCommentToVK("Я, " + userId +
                ", хочу стать администратором Вашего сайта.");
        saveRequest(userId, status);
    }

    @Override
    public boolean getUserRequest(Integer userId) {
        Request request = requestRepository.findRequestById(userId);
        return request != null ? request.isActiveRequest() : false;
    }
}