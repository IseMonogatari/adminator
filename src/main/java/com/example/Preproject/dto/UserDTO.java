package com.example.Preproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String role;
//    private String birthday;

    public UserDTO(String name, String lastName, String email, String password, String role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

//    public UserDTO(String name, String lastName, String email, String password, String role, String birthday) {
//        this.name = name;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//        this.birthday = birthday;
//    }

    public String toStringWithoutPassAndRole() {
        return id + " " + lastName + " " + name + " " + email;
    }
}
