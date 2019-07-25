package ru.palankar.antiviruscontrolscript.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserFilesList {
    private static UserFilesList instance;
    private List<File> userFiles;

    private UserFilesList() {
        userFiles = new ArrayList<>();
    }

    public static UserFilesList getInstance() {
        if (instance == null) {
            instance = new UserFilesList();
        }
        return instance;
    }

    public List<File> getList() {
        return userFiles;
    }

}
