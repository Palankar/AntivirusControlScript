package ru.palankar.antiviruscontrolscript.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Синглтон не для многопоточного использования
 */
public class JSONList {
    private static JSONList instance;
    private List<File> JSONList;

    private JSONList() {
        JSONList = new ArrayList<>();
    }

    public static JSONList getInstance() {
        if (instance == null) {
            instance = new JSONList();
        }
        return instance;
    }

    public List<File> getList() {
        return JSONList;
    }
}
