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

    public DirectoryServiceImpl() {
        logger = LogManager.getLogger(DirectoryServiceImpl.class);
        directories = Directories.getInstance();
    }

    /**
     * Конструктор, вызываемый, когда требуется мгновенная инициализация
     * @param   properties  путь до property-файла
     */
    public DirectoryServiceImpl(String properties) {
        logger = LogManager.getLogger(DirectoryServiceImpl.class);
        directories = Directories.getInstance();
        init(properties);
    }
    /**
     * Инициалиизация директорий из .properties файла
     * Файл обязательно должен лежать в одной директории со скриптом
     */
    @Override
    public void init(String properties) {
        try {
            logger.info("Initializing properties...");
            Properties prop = new Properties();
            prop.load(new FileInputStream(properties));

            setFirstDirectory(prop.getProperty("FirstDirectory"));
            logger.info("Direction property " + prop.getProperty("FirstDirectory") + " initialised");
            setSecondDirectory(prop.getProperty("SecondDirectory"));
            logger.info("Direction property " + prop.getProperty("SecondDirectory") + " initialised");
            setThirdDirectory(prop.getProperty("ThirdDirectory"));
            logger.info("Direction property " + prop.getProperty("ThirdDirectory") + " initialised");
        } catch (IOException e) {
            logger.error("PROPERTY FILE directories.properties COULD NOT FOUND");
            System.exit(0);
            // TODO: 30.07.2019 Пока что все коды возврата на 0, потому что логирую. Потом разобраться и подобрать
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
