package ru.palankar.antiviruscontrolscript.Starters;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.ScriptBody;

public class FirstStarter {
    private static final Logger logger = LogManager.getLogger(FirstStarter.class);

    public static void main(String[] args) {
        logger.info("Starting script initializing");
        ScriptBody scriptBody = new ScriptBody();

        StringBuilder userFiles = new StringBuilder();
        if (userFiles.length() > 0) {
            userFiles.append(scriptBody.getUserFilesWithJsons().values());
            logger.info("Files in first directory: " + userFiles);
        } else {
            logger.info("There are no files in first directory");
        }
    }

}
