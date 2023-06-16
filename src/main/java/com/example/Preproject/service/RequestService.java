package com.example.Preproject.service;

import com.example.Preproject.dto.UserDTO;
import com.example.Preproject.model.User;

import java.util.List;

public interface RequestService {
    List<UserDTO> getUsersByRequest();
    void saveRequest(Integer userId, boolean activeRequest);
    void editRequest(Integer userId, boolean activeRequest);
    void postCommentAdnChangeRequest(Integer userId, boolean activeRequest);
    boolean getUserRequest(Integer userId);
}
