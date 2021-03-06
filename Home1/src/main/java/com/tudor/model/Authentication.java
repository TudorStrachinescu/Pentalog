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

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_authentication_fk"))
    private User user;

    @Column(name = "token", nullable = false)
    private String token;

    public Authentication() {
    }

    public Authentication(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
