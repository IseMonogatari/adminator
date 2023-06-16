package com.example.Preproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "duck")
@Getter
@Setter
@NoArgsConstructor
public class Duck {
    @Id
    private Integer id;
    @Column(name = "picture_url")
    private String pictureUrl;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    public Duck(String pictureUrl, User user) {
        this.pictureUrl = pictureUrl;
        this.user = user;
    }

    public Duck(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
