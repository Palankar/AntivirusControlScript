package ru.palankar.antiviruscontrolscript.Service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Model.Directories;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class DirectoryServiceImpl implements DirectoryService {
    Logger logger = LogManager.getLogger(DirectoryServiceImpl.class);
    //"src/main/resources/directories.properties" - для запуска с IDE
    private static final String PATH_TO_DIR_PROPERTIES = System.getProperty("user.dir") + "\\directories.properties";

    @Override
    public void init() {
        try {
            logger.info("Initializing properties...");
            Properties prop = new Properties();
            prop.load(new FileInputStream(PATH_TO_DIR_PROPERTIES));

            Directories directories = Directories.getInstance();
            directories.setFirstDirectory(prop.getProperty("FirstDirectory"));
            logger.info("Direction property " + prop.getProperty("FirstDirectory") + " initialised");
            directories.setSecondDirectory(prop.getProperty("SecondDirectory"));
            logger.info("Direction property " + prop.getProperty("SecondDirectory") + " initialised");
            directories.setThirdDirectory(prop.getProperty("ThirdDirectory"));
            logger.info("Direction property " + prop.getProperty("ThirdDirectory") + " initialised");
        } catch (IOException e) {
            // TODO: 25.07.2019 Logger warn о ненахождении файла
            logger.error("PROPERTY FILE directories.properties COULD NOT FOUND");

        }
    }
}
