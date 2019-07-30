package ru.palankar.antiviruscontrolscript.Starters;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.Model.JSONList;
import ru.palankar.antiviruscontrolscript.Model.JSONtoUserFileMap;
import ru.palankar.antiviruscontrolscript.Model.UserFilesList;
import ru.palankar.antiviruscontrolscript.Service.DirectoryService;
import ru.palankar.antiviruscontrolscript.Service.DirectoryServiceImpl;
import ru.palankar.antiviruscontrolscript.Service.FileService;
import ru.palankar.antiviruscontrolscript.Service.WinCmdFileService;


public class WinCmdStarter {
    //"src/main/resources/directories.properties" - для запуска с IDE
    //System.getProperty("user.dir") + "\\directories.properties" - для хапуска с билда
    private static final String PATH_TO_DIR_PROPERTIES = "src/main/resources/irectories.properties";

    private static Logger logger = LogManager.getLogger(WinCmdStarter.class);
    private static DirectoryService dirService = new DirectoryServiceImpl(PATH_TO_DIR_PROPERTIES);
    private static FileService fileService = new WinCmdFileService(dirService.getFirstDirectory());
    private static JSONtoUserFileMap jsonToUserFileMap = JSONtoUserFileMap.getInstance();
    private static UserFilesList userFilesList = UserFilesList.getInstance();
    private static JSONList jsonList = JSONList.getInstance();

    public static void main(String[] args) {
        if (jsonToUserFileMap.getMap().size() > 0) {
            fileService.moveFiles(userFilesList.getList(), dirService.getFirstDirectory(), dirService.getSecondDirectory());
            fileService.checkByAntivirus(jsonToUserFileMap.getMap());
            fileService.moveFiles(userFilesList.getList(), dirService.getSecondDirectory(), dirService.getThirdDirectory());
            fileService.moveFiles(jsonList.getList(), dirService.getFirstDirectory(), dirService.getThirdDirectory());
        } else {
            logger.warn("Required files not found");
        }
    }
}
