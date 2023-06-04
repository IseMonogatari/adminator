package com.example.Preproject.dto;

import com.example.Preproject.model.User;
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
    private String birthday;
    private Integer age;

    public UserDTO(String name, String lastName, String email, String password, String role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDTO(String name, String lastName, String email, String password, String role, String birthday) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
    }

    public UserDTO(String id, String name, String lastName, String email, String password, String role,
                   String birthday, Integer age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.age = age;
    }

    public UserDTO(User user, Integer age) {
        this.id = user.getId().toString();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getAllRoles();
        this.birthday = user.getBirthday().toString();
        this.age = age;
    }

    public String toStringWithoutPassAndRole() {
        return id + " " + lastName + " " + name + " " + email;
    }
}
