package com.example.Preproject.controller.rest;

import com.example.Preproject.dto.DuckDTO;
import com.example.Preproject.service.API.DuckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ducks")
public class DuckRC {
    @Autowired
    private DuckService duckService;

    @GetMapping
    public String getUrlForDuck() {
        return duckService.getDuck().getUrl();
    }

    @GetMapping("/all")
    public List<DuckDTO> getAllDucks() {
        return duckService.getAllDuck();
    }
}
