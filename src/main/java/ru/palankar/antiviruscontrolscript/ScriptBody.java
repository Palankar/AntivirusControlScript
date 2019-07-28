package ru.palankar.antiviruscontrolscript;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Model.Directories;
import ru.palankar.antiviruscontrolscript.Model.JSONList;
import ru.palankar.antiviruscontrolscript.Model.JSONtoUserFileMap;
import ru.palankar.antiviruscontrolscript.Model.UserFilesList;
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
    private DirectoryService directoryService;
    private Logger logger = LogManager.getLogger(ScriptBody.class);
    private JSONList jsonList;
    private UserFilesList userFilesList;
    private JSONtoUserFileMap jsonToUserFileMap;

    public ScriptBody() {
        logger.info("Starting initializing...");
        directoryService = new DirectoryServiceImpl();
        directoryService.init();
        jsonList = JSONList.getInstance();
        userFilesList = UserFilesList.getInstance();
        jsonToUserFileMap = JSONtoUserFileMap.getInstance();
        logger.info("Initializing complete...");
    }

    public void startScript() {
        fillingArray();

        if (jsonToUserFileMap.getMap().size() > 0) {
            movingFiles(userFilesList.getList(), directoryService.getFirstDirectory(), directoryService.getSecondDirectory());
        } else {
            logger.info("File/json pairs were not found in the directory " + directoryService.getFirstDirectory());
        }
        checkByAntivirus(jsonToUserFileMap.getMap());

        movingFiles(userFilesList.getList(), directoryService.getSecondDirectory(), directoryService.getThirdDirectory());

    }

    private void fillingArray() {
        File[] files = directoryService.getFirstDirectory().toFile().listFiles();

        if (files.length == 0) {
            logger.info("Files are missing in the specified directory");
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
            logger.info("JSON files missing");

        return jsons;
    }

    private void findingPairs(List<File> jsons) {
        try {
            List<Path> files = Files.walk(Paths.get(directoryService.getFirstDirectory().toString()))
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toFile().getName().endsWith(".json"))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (Path file : files) {
                for (File json : jsons) {
                    if (file.toFile().getName().contains(
                            json.getName()
                                    .substring(0, json.getName().indexOf(".json")))) {
                        jsonToUserFileMap.getMap().put(json.getName(), file.toFile());
                        userFilesList.getList().add(file.toFile());
                    }
                }
            }
        } catch (IOException e) {
            logger.info("The problem is in finding couples file/json");
        }
    }

    // TODO: 25.07.2019 Очень сомнительный кусок, особенно в плане переименований. На рефакторинг 
    /**
     * Перемещение файла, внутри которого происходит переименование
     * @param files ничто иное, как файлы userFilesList
     * @param from начальная директория, где лежит файл
     * @param into конечная директория, где файл окажется
     */
    private void movingFiles(List<File> files, Path from, Path into) {

        logger.info("Start moving files from directory: " + from.toString());

        String filename = null;
        String filepath  = null;
        for (File file : files) {
            File renamed = renameFile(file, file.getPath() + ".part");

            replaceFiles(files, file, renamed);

            filename = file.getName() + ".part";
            filepath = file.getPath() + ".part";

            File displaced = savingFile(renamed, into);

            String key = file.getName().split("[.]")[0]+".json";

            jsonToUserFileMap.getMap().replace(key, displaced);
            Collections.replaceAll(files, renamed, displaced);

            if (renamed.delete())
                logger.info("Файл " + filename + " удален из " + filepath);
        }

        logger.info("Files moved to directory: " + into.toString());
    }

    private File savingFile(File file, Path into) {
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

        return newFile;
    }

    public File renameFile(File file, String newName) {
        File renamedFile = new File(newName);
        file.renameTo(renamedFile);

        return renamedFile;
    }

    public void replaceFiles(List<File> oldUserList, File oldFile, File renamed) {
        for (String key : jsonToUserFileMap.getMap().keySet()) {
            if (key.contains(oldFile.getName().substring(0, oldFile.getName().indexOf('.'))))
                jsonToUserFileMap.getMap().replace(key, renamed);
        }
        Collections.replaceAll(oldUserList, oldFile, renamed);
    }

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
}