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

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_person_fk"))
    private User user;

    @Column(name = "address")
    private String address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Person() {
    }

    public Person(User user, String address, String firstName, String lastName) {
        this.user = user;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
