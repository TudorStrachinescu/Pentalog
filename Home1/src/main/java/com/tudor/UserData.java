package com.tudor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class UserData {

    private static final UserData instance = new UserData();
    private List<User> users = new ArrayList<>();

    private UserData(){}

    static UserData getInstance(){
        return UserData.instance;
    }

    boolean loadUsers(Path path){

        if(!Files.exists(path)){
            return false;
        }

        try(BufferedReader br = Files.newBufferedReader(path)){
            String tmp;
            while((tmp = br.readLine()) != null){
                String[] data = tmp.split(" ");
                if(data.length  == 2){
                    User newUser = new User(data[0], data[1]);
                    users.add(newUser);
                }
            }
        } catch (IOException e){
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