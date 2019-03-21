package com.tudor.users;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class UserData{

    private static final UserData instance = new UserData();
    private List<User> users = new ArrayList<>();

    private UserData(){}

    public static UserData getInstance(){
        return UserData.instance;
    }

    public void loadUsers (Path path) throws LoadFileException{
        String fileName = path.getFileName().toString();

        if(!Files.exists(path)){
            throw new LoadFileException("File " + fileName + " not found");
        }

        try(BufferedReader br = Files.newBufferedReader(path)){
            String tmp;
            while((tmp = br.readLine()) != null){
                String[] data = tmp.split(" ");
                if(data.length  == 2){
                    User newUser = new User(data[0], data[1]);
                    if(!checkUser(newUser)) {
                        this.users.add(newUser);
                    }
                }
            }
        } catch (IOException e){
            throw new LoadFileException("Error retrieving data: " + e.getMessage());
        }

        if(users.size() == 0) {
            throw new LoadFileException(fileName + " does not contain valid data");
        }
    }

    public boolean checkUser(User user){
        if(this.users.size() > 0){
            for(User u : this.users){
                if(u.equals(user)){
                    return true;
                }
            }
        }

        return false;
    }
}