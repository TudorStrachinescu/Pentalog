package com.tudor.modelClasses;

import java.util.Objects;

/**
 *
 */

public class User {
    private String name;
    private String password;

    /**
     *
     * @param name
     * @param password
     */

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     *
     * @param o
     * @return
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && password.equals(user.password);
    }

    /**
     *
     * @return
     */

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     *
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */

    @Override
    public String toString() {
        return super.toString();
    }
}