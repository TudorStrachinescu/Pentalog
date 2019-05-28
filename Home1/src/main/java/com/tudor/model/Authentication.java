package com.tudor.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authentication")

public class Authentication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer user;

    @Column(name = "token", nullable = false)
    private String token;

    public Authentication() {
    }

    public Authentication(Integer user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
