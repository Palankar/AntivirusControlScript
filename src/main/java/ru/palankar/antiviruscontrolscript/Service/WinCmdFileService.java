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

public class WinCmdFileService extends CommandServiceImpl implements FileService {
    private Logger logger = LogManager.getLogger(WinCmdFileService.class);
    private JSONtoUserFileMap jsonToUserFileMap = JSONtoUserFileMap.getInstance();
    private UserFilesList userFilesList = UserFilesList.getInstance();
    private JSONList jsonList = JSONList.getInstance();

    public WinCmdFileService(Path directory) {
        fillArray(directory);
    }

    /**
     * Перемещает набор файлов из одной директории в другую
     * @param   files   исходная коллекция файлов
     * @param   from    начальная директория
     * @param   into    конечная директория
     */
    @Override
    public void moveFiles(List<File> files, Path from, Path into) {
        logger.info("Moving files from " + from.toString() + " to " + into.toString());
        File movedStart = null;
        for (File file : files) {
            File renamed = renameFile(file, FilenameUtils.getName(file.getName()) + ".part");
            logger.info("Moving " + renamed.getName());

            runCmd("move \"" + renamed.getPath() + "\" \"" + into.toString() + "\"");

            movedStart = new File(into.toString() + "\\" + FilenameUtils.getName(renamed.getName()));
            updateFiles(renamed, movedStart);
            renameFile(movedStart, FilenameUtils.getBaseName(renamed.getName()));
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
    public void saveFile(File file, Path into) {
        logger.info("Start saving " + file.getName() + " to " + into.toString() + "...");
        File renamed = renameFile(file, FilenameUtils.getName(file.getName()) + ".part");
        logger.info("Coping " + renamed.getPath() + " to " + into.toString());

        runCmd("copy \"" + renamed.getPath() + "\" \"" + into.toString() + "\"");

        File savedStart = new File(into.toString() + "\\" + FilenameUtils.getName(renamed.getName()));
        renameFile(savedStart, FilenameUtils.getBaseName(renamed.getName()));
        logger.info("Saving correct");
    }

    /**
     * Использует для переименования команду cmd: 'rename fileName newFileName'
     * @param   file    исходный файл
     * @param   newName новое имя файла
     * @return  создает File с новым именем по пути старого и возвращает его
     */
    @Override
    public File renameFile(File file, String newName) {
        runCmd("rename \"" + file.getPath() + "\" " + newName);

        File renamed = new File(FilenameUtils.getFullPath(file.getPath()) + newName);
        updateFiles(file, renamed);

        return renamed;
    }

    /**
     * Обновляет файлы в коллекциях jsonToUserFileMap и userFilesList и
     * JSON в коллекции jsonList
     * @param   oldFile     старый файл, изначально находящийся в коллекции
     * @param   newFile     новый файл, заменяющий старый в коллекции
     */
    @Override
    public void updateFiles(File oldFile, File newFile) {
        if (!oldFile.getName().contains(".json")) {
            for (String key : jsonToUserFileMap.getMap().keySet()) {
                String val = oldFile.getName().split("[.]")[0];
                if (key.contains(val))
                    jsonToUserFileMap.getMap().replace(key, newFile);
            }
            if (!Collections.replaceAll(userFilesList.getList(), oldFile, newFile))
                logger.warn("Failed to update files");
        } else {
            if (!Collections.replaceAll(jsonList.getList(), oldFile, newFile))
                logger.warn("Failed to update jsons");
        }
    }

    /**
     * Проверка антивирусом. Пока что чисто фиктивная
     * @param   files   коллекция, содержащая файлы на проверку антивирусом
     */
    @Override
    public void checkByAntivirus(Map<String, File> files) {
        logger.info("Yay, anti-virus scanning! :3");

        boolean isVirus = false;
        File renamed = null;
        for (File file : files.values()) {
            renamed = renameFile(file, file.getName() + ".checking");
            logger.info("Scanning " + renamed);
            renameFile(renamed, FilenameUtils.removeExtension(renamed.getName()));
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

        runCmd("del \"" + file.getPath() + "\"");

        logger.info("File " + file.getName() + " deleted");
    }

    private void fillArray(Path directory) {
        File[] files = directory.toFile().listFiles();

        if (files.length == 0) {
            logger.info("Files are missing in the specified directory");
        } else {
            List<File> jsons = filterArray(files);
            findPairs(jsons, directory);

            for (File json : jsons) {
                String jsName = FilenameUtils.getName(json.getName());
                if (jsonToUserFileMap.getMap().containsKey(jsName))
                    jsonList.getList().add(json);
            }
        }
    }

    private List<File> filterArray(File[] files) {
        ArrayList<File> jsons = new ArrayList<>();

        for (File json : files) {
            if (json.getName().endsWith(".json"))
                jsons.add(json);
        }

        if (jsons.size() == 0)
            logger.info("JSON files missing");

        return jsons;
    }

    private void findPairs(List<File> jsons, Path directory) {
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
}
