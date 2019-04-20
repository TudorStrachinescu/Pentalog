package com.tudor.modelClasses;

import java.util.Objects;

/**
 * Represents a user of a bank.
 */

public class User {
    private String name;
    private String password;

    /**
     * Creates a new User with the given parameters.
     *
     * @param name      the name for the User
     * @param password  the password for the User
     */

    public User(String name, String password) {
        this.name = name;
        this.password = password;
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