package com.example.Preproject.dto;

import com.example.Preproject.model.User;
import com.example.Preproject.util.FormatterUtils;
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

    public UserDTO(User user, Integer age) {
        this.id = user.getId().toString();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getAllRoles();
        this.birthday = user.getBirthday().format(FormatterUtils.defaultDateFormatter());
        this.age = age;
    }

    public String toStringWithoutPassAndRole() {
        return id + " " + lastName + " " + name + " " + email + " " + birthday;
    }
}
