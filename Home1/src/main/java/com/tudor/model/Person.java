package com.tudor.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "person")

public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_person_fk"))
    private User user;

    @Column(name = "address")
    private String address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public Person() {
    }

    public Person(User user, String address, String firstName, String lastName, String email) {
        this.user = user;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
