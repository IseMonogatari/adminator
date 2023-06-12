package com.example.Preproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myLogin")
public class MainController {

    @GetMapping
    public String login() {
        return "myLogin";
    }
}
