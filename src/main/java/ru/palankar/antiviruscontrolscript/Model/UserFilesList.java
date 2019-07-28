package ru.palankar.antiviruscontrolscript.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Синглтон List с файлами
 */
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
