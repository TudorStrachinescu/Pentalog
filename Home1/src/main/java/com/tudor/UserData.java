package com.tudor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

final class UserData {

    private static final UserData instance = new UserData();
    private List<User> users = new ArrayList<>();

    private UserData(){}

    static UserData getInstance(){
        return UserData.instance;
    }

    boolean loadUsers(String path){

        if(path == null){
            return false;
        }

        File file = new File(path);

        try(Scanner s = new Scanner(file)){
            while(s.hasNextLine()){
                String[] data = s.nextLine().split(" ");
                if(data.length == 2){
                    User newUser = new User(data[0], data[1]);
                    users.add(newUser);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return (users.size() > 0);
    }

    boolean exists(String name, String password){
        if(users.size() > 0){
            for(User u : users){
                if(u.userMatch(name, password)){
                    return true;
                }
            }
        }

        return false;
    }
}