package ru.palankar.antiviruscontrolscript.Service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Model.Directories;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DirectoryServiceImpl implements DirectoryService {
    private Logger logger;
    private Directories directories;

    //"src/main/resources/directories.properties" - для запуска с IDE
    //System.getProperty("user.dir") + "\\directories.properties" - для хапуска с билда
    private static final String PATH_TO_DIR_PROPERTIES = "src/main/resources/directories.properties";


    public DirectoryServiceImpl() {
        logger = LogManager.getLogger(DirectoryServiceImpl.class);
        directories = Directories.getInstance();
    }
    /**
     * Инициалиизация директорий из .properties файла
     * Файл обязательно должен лежать в одной директории со скриптом
     */
    @Override
    public void init() {
        try {
            logger.info("Initializing properties...");
            Properties prop = new Properties();
            prop.load(new FileInputStream(PATH_TO_DIR_PROPERTIES));

            setFirstDirectory(prop.getProperty("FirstDirectory"));
            logger.info("Direction property " + prop.getProperty("FirstDirectory") + " initialised");
            setSecondDirectory(prop.getProperty("SecondDirectory"));
            logger.info("Direction property " + prop.getProperty("SecondDirectory") + " initialised");
            setThirdDirectory(prop.getProperty("ThirdDirectory"));
            logger.info("Direction property " + prop.getProperty("ThirdDirectory") + " initialised");
        } catch (IOException e) {
            logger.error("PROPERTY FILE directories.properties COULD NOT FOUND");
        }
        logger.info("Initializing complete");
    }

    @Override
    public Path getFirstDirectory() {
        return directories.getFirstDirectory();
    }

    @Override
    public void setFirstDirectory(String firstDirectory) {
        directories.setFirstDirectory(firstDirectory);
    }

    @Override
    public Path getSecondDirectory() {
        return directories.getSecondDirectory();
    }

    @Override
    public void setSecondDirectory(String secondDirectory) {
        directories.setSecondDirectory(secondDirectory);
    }

    @Override
    public Path getThirdDirectory() {
        return directories.getThirdDirectory();
    }

    @Override
    public void setThirdDirectory(String thirdDirectory) {
        directories.setThirdDirectory(thirdDirectory);
    }
}
