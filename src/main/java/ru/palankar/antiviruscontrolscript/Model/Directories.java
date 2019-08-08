package ru.palankar.antiviruscontrolscript.Model;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Директории, по которым перемещаются файлы.
 * Задаются через .properties файл.
 */
public class Directories {
    private static Directories instance;
    private String firstDirectory;
    private String secondDirectory;
    private String thirdDirectory;
    private String errorsDirectory;
    private String kavshell;


    private Directories() { }

    public static Directories getInstance() {
        if (instance == null) {
            instance = new Directories();
        }
        return instance;
    }

    public Path getFirstDirectory() {
        return Paths.get(firstDirectory);
    }

    public void setFirstDirectory(String firstDirectory) {
        this.firstDirectory = firstDirectory;
    }

    public Path getSecondDirectory() {
        return Paths.get(secondDirectory);
    }

    public void setSecondDirectory(String secondDirectory) {
        this.secondDirectory = secondDirectory;
    }

    public Path getThirdDirectory() {
        return Paths.get(thirdDirectory);
    }

    public void setThirdDirectory(String thirdDirectory) {
        this.thirdDirectory = thirdDirectory;
    }
    
    public Path getErrorsDirectory() {
        return Paths.get(errorsDirectory);
    }

    public void setErrorsDirectory(String errorsDirectory) {
        this.errorsDirectory = errorsDirectory;
    }

    public Path getKavshellDirectory() {
        return Paths.get(kavshell);
    }

    public void setKavshellDirectory(String kavshell) {
        this.kavshell = kavshell;
    }
}
