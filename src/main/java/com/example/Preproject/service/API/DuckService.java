package com.example.Preproject.service.API;

import com.example.Preproject.dto.DuckDTO;
import com.example.Preproject.model.User;

import java.util.List;

public interface DuckService {
    DuckDTO findDuckURLFromDB(User user);
    DuckDTO getDuck();
    void saveDuck(User user);
    List<DuckDTO> getAllDuck();
}
