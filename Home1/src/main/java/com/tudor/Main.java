package com.tudor;

import com.tudor.appMenu.Menu;
import com.tudor.exceptions.LoadFileException;
import com.tudor.users.UserData;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    private static final String FILE_PATH = "src" + File.separator + "main" +
            File.separator + "resources" + File.separator +"data.txt";

    public static void main(String[] args) {
        UserData data = UserData.getInstance();

        Path path = FileSystems.getDefault().getPath(FILE_PATH);

        try {
            data.loadUsers(path);
        } catch (LoadFileException e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Data loaded");

        Menu menu = new Menu();
        menu.runApp();
    }


}