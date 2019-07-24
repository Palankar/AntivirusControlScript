package ru.palankar.antiviruscontrolscript;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
    Logger logger = LogManager.getLogger(ScriptBody.class);
    private static final String PATH_TO_DIR_PROPERTIES = "src/main/resources/directories.properties";

    private Path firstDirectory;
    private Path secondDirectory;
    private Path thirdDirectory;
    private List<File> JSONList = new ArrayList<>();
    private Map<String, File> userFilesWithJsons = new HashMap<>();
    private List<File> userFiles = new ArrayList<>();   //к которым нашли json


    public ScriptBody() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(PATH_TO_DIR_PROPERTIES));

            this.firstDirectory = Paths.get(properties.getProperty("FirstDirectory"));
            this.secondDirectory = Paths.get(properties.getProperty("SecondDirectory"));
            this.thirdDirectory = Paths.get(properties.getProperty("ThirdDirectory"));
        } catch (IOException e) {
            logger.info("Не вышло обратиться к конфигурации");
        }

        startScript();
    }

    public List getJSONList() {
        return JSONList;
    }

    public void setJSONList(List JSONList) {
        this.JSONList = JSONList;
    }

    public Path getFirstDirectory() {
        return firstDirectory;
    }

    public Path getSecondDirectory() {
        return secondDirectory;
    }

    public Path getThirdDirectory() {
        return thirdDirectory;
    }

    private void fillingArray() {
        File[] files = getFirstDirectory().toFile().listFiles();

        if (files.length == 0) {
            logger.info("Файлы отсутствуют в указанной директории");
        } else {
            List<File> jsons = filterArray(files);
            findingPairs(jsons);
            JSONList.addAll(jsons);
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
            List<Path> files = Files.walk(Paths.get(firstDirectory.toString()))
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.toFile().getName().endsWith(".json"))
                    .collect(Collectors.toCollection(ArrayList::new));

            for (Path file : files) {
                for (File json : jsons) {
                    if (file.toFile().getName().contains(
                            json.getName()
                                    .substring(0, json.getName().indexOf(".json")))) {
                        userFilesWithJsons.put(json.getName(), file.toFile());
                        userFiles.add(file.toFile());
                    }
                }
            }
        } catch (IOException e) {
            logger.info("Проблема при нахождении пар файл/json");
        }
    }

    public void startScript() {
        fillingArray();
        if (userFilesWithJsons.size() > 0) {
            movingFiles(userFiles, firstDirectory, secondDirectory);
            for (File file : userFiles) {
                replaceFiles(userFiles, file,
                        renameFile(file, file.getPath().substring(0, file.getPath().indexOf(".part"))));
            }
        } else {
            logger.info("Пар файл/json не было обнаружено в директории " + firstDirectory);
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
        for (String key : userFilesWithJsons.keySet()) {
            if (key.contains(oldFile.getName().substring(0, oldFile.getName().indexOf('.'))))
                userFilesWithJsons.replace(key, renamed);
        }
        Collections.replaceAll(oldUserList, oldFile, renamed);
    }
}