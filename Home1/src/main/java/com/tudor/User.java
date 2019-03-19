package com.tudor;

class User {
    private String name;
    private String password;

    User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    String getName() {
        return name;
    }

    boolean userMatch(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}