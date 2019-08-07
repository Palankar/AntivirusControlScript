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

public class ScriptBody {
    //"src/main/resources/directories.json" - для запуска с IDE
    //System.getProperty("user.dir") + "\\directories.json" - для хапуска с билда
    private static final String PATH_TO_DIR_PROPERTIES = "src/main/resources/directories.json";

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
            fileService.checkByAntivirus(jsonToUserFileMap.getMap());
            fileService.moveFiles(userFilesList.getList(),
                    dirService.getSecondDirectory(), dirService.getThirdDirectory());
            fileService.moveFiles(jsonList.getList(),
                    dirService.getFirstDirectory(), dirService.getThirdDirectory());
        } else {
            logger.warn("Required files not found");
        }

        logger.info("Script complete");
    }

    // TODO: 01.08.2019 Вынести особую логику в тело скрипта, а в сервисах оставить лишь универсальную для любого скрипта 
}