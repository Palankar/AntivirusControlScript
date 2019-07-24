package ru.palankar.antiviruscontrolscript.Model;

import java.nio.file.Path;

/**
 * Директории, по которым перемещаются файлы.
 * Задаются через .properties файл.
 */
public class Directories {
    private Path firstDirectory;
    private Path secondDirectory;
    private Path thirdDirectory;

    public Path getFirstDirectory() {
        return firstDirectory;
    }

    public void setFirstDirectory(Path firstDirectory) {
        this.firstDirectory = firstDirectory;
    }

    public Path getSecondDirectory() {
        return secondDirectory;
    }

    public void setSecondDirectory(Path secondDirectory) {
        this.secondDirectory = secondDirectory;
    }

    public Path getThirdDirectory() {
        return thirdDirectory;
    }

    public void setThirdDirectory(Path thirdDirectory) {
        this.thirdDirectory = thirdDirectory;
    }
}
