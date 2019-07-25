package ru.palankar.antiviruscontrolscript.Service;

import ru.palankar.antiviruscontrolscript.Model.Directories;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class DirectoryServiceImpl implements DirectoryService {
    private static final String PATH_TO_DIR_PROPERTIES = "src/main/resources/directories.properties";

    @Override
    public void init() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(PATH_TO_DIR_PROPERTIES));

            Directories directories = Directories.getInstance();
            directories.setFirstDirectory(prop.getProperty("FirstDirectory"));
            directories.setSecondDirectory(prop.getProperty("SecondDirectory"));
            directories.setThirdDirectory(prop.getProperty("ThirdDirectory"));
        } catch (IOException e) {
            // TODO: 25.07.2019 Logger warn о ненахождении файла 
        }
    }
}
