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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;


public class WinCmdStarter {
    private static File file = new File("D:\\A-repository (reserve)\\test.json");

    /*
    //"src/main/resources/directories.json" - для запуска с IDE
    //System.getProperty("user.dir") + "\\directories.json" - для хапуска с билда
    private static final String PATH_TO_DIR_PROPERTIES = "src/main/resources/directories.json";

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
    */

    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));
        writer.print("\r\nNEW TEXT");
        writer.print("\r\nНОВЫЙ ТЕКСТ");
        writer.flush();
        writer.close();
    }
}
