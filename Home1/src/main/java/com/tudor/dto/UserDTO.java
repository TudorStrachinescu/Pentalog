package com.tudor.dto;

import com.tudor.model.Person;

public class UserDTO {
    private String name;
    private Person details;

    public UserDTO() {
    }

    public UserDTO(String name){
        this.name = name;
    }

    public UserDTO(String name, Person details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(Person details) {
        this.details = details;
    }

    public Person getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", details=" + details +
                '}';
    }
}
