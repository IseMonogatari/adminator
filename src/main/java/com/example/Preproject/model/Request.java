package com.example.Preproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "request")
@NoArgsConstructor
@Getter
@Setter
public class Request {
    @Id
    private Integer id;
    @Column(name = "active_request")
    private boolean activeRequest;
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    public Request(boolean activeRequest, User user) {
        this.activeRequest = activeRequest;
        this.user = user;
    }

    @Override
    public String toString() {
        return "\nRequest{" +
                "id=" + id +
                ", activeRequest=" + activeRequest +
                '}';
    }
}
