package ru.palankar.antiviruscontrolscript;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Model.JSONList;
import ru.palankar.antiviruscontrolscript.Model.JSONtoUserFileMap;
import ru.palankar.antiviruscontrolscript.Model.UserFilesList;
import ru.palankar.antiviruscontrolscript.Service.DirectoryService;
import ru.palankar.antiviruscontrolscript.Service.DirectoryServiceImpl;
import ru.palankar.antiviruscontrolscript.Service.FileService;
import ru.palankar.antiviruscontrolscript.Service.WinCmdFileService;

import java.io.File;

public class ScriptBody {
    //"src/main/resources/directories.properties" - для запуска с IDE
    //System.getProperty("user.dir") + "\\directories.properties" - для хапуска с билда
    private static final String PATH_TO_DIR_PROPERTIES = "src/main/resources/directories.properties";

    private Logger logger = LogManager.getLogger(ScriptBody.class);
    private static DirectoryService dirService;
    private static FileService fileService;
    private JSONList jsonList;
    private UserFilesList userFilesList;
    private JSONtoUserFileMap jsonToUserFileMap;

    public ScriptBody() {
        dirService = new DirectoryServiceImpl(PATH_TO_DIR_PROPERTIES);
        fileService = new WinCmdFileService(dirService.getFirstDirectory());
        jsonList = JSONList.getInstance();
        userFilesList = UserFilesList.getInstance();
        jsonToUserFileMap = JSONtoUserFileMap.getInstance();
    }

    public void startScript() {
        logger.info("Starting script...");

        if (jsonToUserFileMap.getMap().size() > 0) {
            fileService.moveFiles(userFilesList.getList(),
                    dirService.getFirstDirectory(), dirService.getSecondDirectory());
            if (fileService.checkByAntivirus(jsonToUserFileMap.getMap())) {
                for (File file : userFilesList.getList()) {
                    fileService.deleteFile(file);
                }
            } else {
                fileService.moveFiles(userFilesList.getList(),
                        dirService.getSecondDirectory(), dirService.getThirdDirectory());
                fileService.moveFiles(jsonList.getList(),
                        dirService.getFirstDirectory(), dirService.getThirdDirectory());
            }
        } else {
            logger.warn("Required files not found");
        }

        logger.info("Script complete");
    }

}