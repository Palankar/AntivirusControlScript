package ru.palankar.antiviruscontrolscript.Model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Синглтон Map для файлов в формате key - json/value - связанный с ниим файл
 */
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
