package com.tudor.modelClasses;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user of a bank.
 */

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "created_time")
    private LocalDateTime created;

    @Column(name = "updated_time")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "notificationUser")
    List<Notification> notifications;

    @OneToMany(mappedBy = "accountUser")
    List<Account> accounts;

    /**
     * Creates a new User with the given parameters.
     *
     * @param name      the name for the User
     * @param password  the password for the User
     */

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        created = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    /**
     * Gets the name for the User
     *
     * @return name
     */

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}