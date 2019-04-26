package com.tudor.modelClasses;

import javax.persistence.*;

@Entity
@Table(name = "person")

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address")
    private String address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Person(User user, String address, String firstName, String lastName) {
        this.user = user;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
