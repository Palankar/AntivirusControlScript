package ru.palankar.antiviruscontrolscript;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Model.Directories;
import ru.palankar.antiviruscontrolscript.Repository.JSONList;
import ru.palankar.antiviruscontrolscript.Repository.JSONtoUserFileMap;
import ru.palankar.antiviruscontrolscript.Repository.UserFilesList;
import ru.palankar.antiviruscontrolscript.Service.DirectoryService;
import ru.palankar.antiviruscontrolscript.Service.DirectoryServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ScriptBody {
    private Directories directories;
    private Logger logger = LogManager.getLogger(ScriptBody.class);
    private JSONList jsonList;
    private UserFilesList userFilesList;
    private JSONtoUserFileMap jsoNtoUserFileMap;

    public ScriptBody() {
        directories = Directories.getInstance();
        DirectoryService directoryService = new DirectoryServiceImpl();
        directoryService.init();
        jsonList = JSONList.getInstance();
        userFilesList = UserFilesList.getInstance();
        jsoNtoUserFileMap = JSONtoUserFileMap.getInstance();

        startScript();
    }

    public void startScript() {
        fillingArray();
        if (jsoNtoUserFileMap.getMap().size() > 0) {
            movingFiles(userFilesList.getList(), directories.getFirstDirectory(), directories.getSecondDirectory());
            for (File file : userFilesList.getList()) {
                replaceFiles(userFilesList.getList(), file,
                        renameFile(file, file.getPath().substring(0, file.getPath().indexOf(".part"))));
            }
        } else {
            logger.info("Пар файл/json не было обнаружено в директории " + directories.getFirstDirectory());
        }
    }

    private void fillingArray() {
        File[] files = directories.getFirstDirectory().toFile().listFiles();

        if (files.length == 0) {
            logger.info("Файлы отсутствуют в указанной директории");
        } else {
            List<File> jsons = filterArray(files);
            findingPairs(jsons);
            jsonList.getList().addAll(jsons);
        }
    }

    //filtering by adding only jsons
    private List<File> filterArray(File[] files) {
        ArrayList<File> jsons = new ArrayList<>();

        for (File json : files) {
            if (json.getName().endsWith(".json"))
                jsons.add(json);
        }

        if (jsons.size() == 0)
            logger.info("JSON файлы отсутствуют");

        return jsons;
    }

    private void findingPairs(List<File> jsons) {
        try {
            List<Path> files = Files.walk(Paths.get(directories.getFirstDirectory().toString()))
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toFile().getName().endsWith(".json"))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (Path file : files) {
                for (File json : jsons) {
                    if (file.toFile().getName().contains(
                            json.getName()
                                    .substring(0, json.getName().indexOf(".json")))) {
                        jsoNtoUserFileMap.getMap().put(json.getName(), file.toFile());
                        userFilesList.getList().add(file.toFile());
                    }
                }
            }
        } catch (IOException e) {
            logger.info("Проблема при нахождении пар файл/json");
        }
    }

    private void movingFiles(List<File> files, Path from, Path into) {

        logger.info("Начало перемещения файлов из каталога: " + from.toString());

        String filename = null;
        String filepath  = null;
        for (File file : files) {
            File renamed = renameFile(file, file.getPath() + ".part");

            replaceFiles(files, file, renamed);

            filename = file.getName() + ".part";
            filepath = file.getPath() + ".part";

            savingFile(renamed, into);

            if (renamed.delete())
                logger.info("Файл " + filename + " удален из " + filepath);
        }
        logger.info("Файлы перемещены в каталог: " + into.toString());
    }

    private void savingFile(File file, Path into) {
        File newFile = new File(into.toString() + "\\" +
                file.getName().substring(0, file.getName().indexOf(".part")));

        try {
            FileInputStream inStream = new FileInputStream(file);
            FileOutputStream outStream = new FileOutputStream(newFile);

            while (inStream.available() > 0)
                outStream.write(inStream.read());

            inStream.close();
            outStream.close();

        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    public File renameFile(File file, String newName) {
        File renamedFile = new File(newName);
        file.renameTo(renamedFile);

        return renamedFile;
    }

    public void replaceFiles(List<File> oldUserList, File oldFile, File renamed) {
        for (String key : jsoNtoUserFileMap.getMap().keySet()) {
            if (key.contains(oldFile.getName().substring(0, oldFile.getName().indexOf('.'))))
                jsoNtoUserFileMap.getMap().replace(key, renamed);
        }
        Collections.replaceAll(oldUserList, oldFile, renamed);
    }
}