package ru.palankar.antiviruscontrolscript.Starters;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Repository.JSONtoUserFileMap;
import ru.palankar.antiviruscontrolscript.ScriptBody;

public class FirstStarter {
    private static final Logger logger = LogManager.getLogger(FirstStarter.class);
    private static JSONtoUserFileMap jsonNtoUserFileMap = JSONtoUserFileMap.getInstance();

    public static void main(String[] args) {
        logger.info("Starting script initializing");
        ScriptBody scriptBody = new ScriptBody();

        scriptBody.startScript();

        StringBuilder userFiles = new StringBuilder();
        if (userFiles.length() > 0) {
            userFiles.append(jsonNtoUserFileMap.getMap().values());
            logger.info("Files in first directory: " + userFiles);
        } else {
            logger.info("There are no files in first directory");
        }
    }
    // TODO: 25.07.2019 Перед заявлением, что файлы успешно перемещены в указанную директорию нужно проверить, там ли он 
}
