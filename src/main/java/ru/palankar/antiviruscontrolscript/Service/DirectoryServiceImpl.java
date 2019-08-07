package ru.palankar.antiviruscontrolscript.Service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import ru.palankar.antiviruscontrolscript.Model.Directories;
import java.nio.file.Path;

public class DirectoryServiceImpl implements DirectoryService {
    private Logger logger;
    private Directories directories;
    private JSONService jsonService;

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
        jsonService = new JSONServiceImpl();
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
            JSONObject dirJSON = jsonService.getObj(properties);

            setFirstDirectory(dirJSON.get("FirstDirectory").toString());
            logger.info("Direction property " + dirJSON.get("FirstDirectory").toString() + " initialised");
            setSecondDirectory(dirJSON.get("SecondDirectory").toString());
            logger.info("Direction property " + dirJSON.get("SecondDirectory").toString() + " initialised");
            setThirdDirectory(dirJSON.get("ThirdDirectory").toString());
            logger.info("Direction property " + dirJSON.get("ThirdDirectory").toString() + " initialised");
        } catch (Exception e) {
            logger.error("PROPERTY FILE directories.json COULD NOT FOUND");
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
