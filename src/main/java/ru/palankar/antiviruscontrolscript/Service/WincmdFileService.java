package ru.palankar.antiviruscontrolscript.Service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class WincmdFileService implements FileService {
    Logger logger = LogManager.getLogger(WincmdFileService.class);
    FileUtils utils = new FileUtils();

    @Override
    public void movingFiles(List<File> files, Path from, Path into) {
        logger.info("Start moving files from directory: " + from.toString() + " to: " + into.toString());

        String filename = null;
        String filepath  = null;
        for (File file : files) {
            File renamed = renameFile(file, file.getPath() + ".part");

        }

    }

    @Override
    public File savingFile(File file, Path into) {
        return null;
    }

    /**
     * Использует для переименования команду cmd: 'rename fileName newFileName'
     * @param   file    исходный файл
     * @param   newName новое имя файла
     * @return  создает File с новым именем по пути старого и возвращает его
     */
    @Override
    public File renameFile(File file, String newName) {
        try {
            String command = "cmd /c rename " + file.getPath() + " " + newName;
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            logger.error("Error renaming " + file.getName());
        }
        return new File(FilenameUtils.getPath(file.getPath()) + newName);
    }

    @Override
    public void replaceFiles(List<File> oldUserList, File oldFile, File renamed) {

    }

    @Override
    public void checkByAntivirus(Map<String, File> files) {

    }
}
