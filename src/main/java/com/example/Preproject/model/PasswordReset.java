package com.example.Preproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "password_reset_token")
@NoArgsConstructor
@Getter
@Setter
public class PasswordReset {

    @Id
    private Integer id;
    @Column(name = "token")
    private String token;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    public PasswordReset(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
