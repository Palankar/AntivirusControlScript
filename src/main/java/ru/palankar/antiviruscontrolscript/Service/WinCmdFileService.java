package ru.palankar.antiviruscontrolscript.Service;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Model.JSONList;
import ru.palankar.antiviruscontrolscript.Model.JSONtoUserFileMap;
import ru.palankar.antiviruscontrolscript.Model.UserFilesList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WinCmdFileService implements FileService {
    private Logger logger = LogManager.getLogger(WinCmdFileService.class);
    private JSONtoUserFileMap jsonToUserFileMap = JSONtoUserFileMap.getInstance();
    private UserFilesList userFilesList = UserFilesList.getInstance();
    private JSONList jsonList = JSONList.getInstance();

    /**
     * Перемещает набор файлов из одной директории в другую
     * @param   files   исходная коллекция файлов
     * @param   from    начальная директория
     * @param   into    конечная директория
     */
    @Override
    public void movingFiles(List<File> files, Path from, Path into) {
        try {
            logger.info("Moving files from " + from.toString() + " to " + into.toString());
            File moved = null;
            for (File file : files) {
                File renamed = renameFile(file, FilenameUtils.getName(file.getName()) + ".part");
                String command = "cmd /c move " + renamed.getPath() + " " + into.toString();

                Runtime rnt = Runtime.getRuntime();
                Process proc = rnt.exec(command);
                proc.waitFor();

                moved = new File(into.toString() + "\\" + FilenameUtils.getName(renamed.getName()));
                renameFile(moved, FilenameUtils.getBaseName(renamed.getName()));
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Moving files failed");
        }
        logger.info("Moving files complete");
    }

    /**
     * Сохраняет переданный файл в указанную директорию, предварительно добавив к его имени
     * расширение .part во избежание обращения в процессе сохранения со стороны других программ.
     * По завершению процесса сохранения приставка .part удаляется
     * @param   file    файл для сохранения
     * @param   into    директория сохранения
     * @return  File сохраненный в новое место
     */
    @Override
    public File savingFile(File file, Path into) {
        logger.info("Start saving " + file.getName() + " to " + into.toString() + "...");
        File renamed = renameFile(file, FilenameUtils.getName(file.getName()) + ".part");
        File saved = null;
        try {
            logger.info("Coping " + renamed.getPath() + " to " + into.toString());
            String command = "cmd /c copy " + renamed.getPath() + " " + into.toString();

            Runtime rnt = Runtime.getRuntime();
            Process proc = rnt.exec(command);
            proc.waitFor();

            saved = new File(into.toString() + "\\" + FilenameUtils.getName(renamed.getName()));
            renameFile(saved, FilenameUtils.getBaseName(renamed.getName()));
        } catch (IOException | InterruptedException e) {
            logger.error("Error saving " + renamed.getName());
        }
        logger.info("Saving correct");
        return saved;
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

            Runtime rnt = Runtime.getRuntime();
            Process proc = rnt.exec(command);
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.error("Error renaming " + file.getName());
        }
        return new File(FilenameUtils.getFullPath(file.getPath()) + newName);
    }

    /**
     * Обновляет файлы в коллекциях
     * @param   oldUserList имеющаяся коллекция файлов
     * @param   oldFile     старый файл, изначально находящийся в коллекции
     * @param   renamed     новый, переименованный файл, заменяющий старый в коллекции
     */
    @Override
    public void updateFiles(List<File> oldUserList, File oldFile, File renamed) {
        for (String key : jsonToUserFileMap.getMap().keySet()) {
            if (key.contains(FilenameUtils.removeExtension(oldFile.getName())))
                jsonToUserFileMap.getMap().replace(key, renamed);
        }
        if (!Collections.replaceAll(oldUserList, oldFile, renamed))
            logger.warn("Failed to update files");
    }

    /**
     * Проходится по файлам из заданной директории и сопостоявляет их с данными JSON, и
     * если имена совпадают - добавляет их в JSONtoUserFileMap и UserFilesList
     * @param   jsons       заданные JSON
     * @param   directory   директория, в которой осуществляется поиск
     */
    @Override
    public void findingPairs(List<File> jsons, Path directory) {
        // TODO: 28.07.2019 попробовать провести поиск по директории с помощью dir и перехвата данных с консоли
        try {
            List<Path> files = Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toFile().getName().endsWith(".json"))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (Path file : files) {
                for (File json : jsons) {
                    if (file.toFile().getName().contains(
                            FilenameUtils.getBaseName(json.getName()))) {
                        jsonToUserFileMap.getMap().put(json.getName(), file.toFile());
                        userFilesList.getList().add(file.toFile());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("JSON/File pair search error");
        }
    }

    /**
     * Проверка антивирусом. Пока что чисто фиктивная
     * @param   files   коллекция, содержащая файлы на проверку антивирусом
     */
    @Override
    public void checkByAntivirus(Map<String, File> files) {
        logger.info("Yay, anti-virus scanning! :3 :3");

        boolean isVirus = false;
        for (File file : files.values()) {
            logger.info("Scanning " + file);
        }

        if(isVirus)
            logger.info("virus detected!");
        else
            logger.info("Scanning complete");
    }

    /**
     * Удаляет переданный в аргументы файл
     * @param   file    удаляемый файл
     */
    @Override
    public void deleteFile(File file) {
        logger.info("Deleting " + file.getName());
        try {
            String command = "cmd /c del " + file.getPath();

            Runtime rnt = Runtime.getRuntime();
            Process proc = rnt.exec(command);
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.error("Deletion " + file.getName() + " failed");
        }
        logger.info("File " + file.getName() + " deleted");
    }

    @Override
    public void fillingArray(Path directory) {
        File[] files = directory.toFile().listFiles();

        if (files.length == 0) {
            logger.info("Files are missing in the specified directory");
        } else {
            List<File> jsons = filterArray(files);
            findingPairs(jsons, directory);
            jsonList.getList().addAll(jsons);
        }
    }

    @Override
    public List<File> filterArray(File[] files) {
        ArrayList<File> jsons = new ArrayList<>();

        for (File json : files) {
            if (json.getName().endsWith(".json"))
                jsons.add(json);
        }

        if (jsons.size() == 0)
            logger.info("JSON files missing");

        return jsons;
    }

    // TODO: 29.07.2019  filterArray, fillingArray, findingPairs - больше, все же, Util классы приватные. Надо вынести.
}
