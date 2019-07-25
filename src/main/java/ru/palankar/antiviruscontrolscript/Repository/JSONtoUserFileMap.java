package ru.palankar.antiviruscontrolscript.Repository;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JSONtoUserFileMap {
    private static JSONtoUserFileMap instance;
    private Map<String, File> jsonToUserFile;

    public static JSONtoUserFileMap getInstance() {
        if (instance == null) {
            instance = new JSONtoUserFileMap();
        }
        return instance;
    }

    private JSONtoUserFileMap() {
        jsonToUserFile = new HashMap<>();
    }

    public Map<String, File> getMap() {
        return jsonToUserFile;
    }
}
