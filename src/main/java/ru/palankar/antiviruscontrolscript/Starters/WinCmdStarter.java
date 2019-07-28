package ru.palankar.antiviruscontrolscript.Starters;

import ru.palankar.antiviruscontrolscript.Model.JSONList;
import ru.palankar.antiviruscontrolscript.Model.JSONtoUserFileMap;
import ru.palankar.antiviruscontrolscript.Model.UserFilesList;
import ru.palankar.antiviruscontrolscript.Service.DirectoryService;
import ru.palankar.antiviruscontrolscript.Service.DirectoryServiceImpl;
import ru.palankar.antiviruscontrolscript.Service.FileService;
import ru.palankar.antiviruscontrolscript.Service.WinCmdFileService;


public class WinCmdStarter {
    private static FileService fileService = new WinCmdFileService();
    private static DirectoryService dirService = new DirectoryServiceImpl();
    private static JSONtoUserFileMap jsonToUserFileMap = JSONtoUserFileMap.getInstance();
    private static UserFilesList userFilesList = UserFilesList.getInstance();
    private static JSONList jsonList = JSONList.getInstance();

    public static void main(String[] args) throws Exception {
        //СРАБОТАЛО!
        /*
        ByteArrayOutputStream arrOutStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(arrOutStream);
        String command = "cmd /c dir G:\\C-repository";

        System.setOut(out);
        Runtime.getRuntime().exec(command);

        System.out.println(new String(arrOutStream.toByteArray()));
        //!!!ЗАБЫЛИ ДОБАВИТЬ В КОНЦЕ ПЕРЕД ВЫВОДОМ ВОЗВРАТ НА МЕСТО OUT!!!
         */
/*
        File file = new File("G:\\C-repository\\test.txt");
        file.createNewFile();
        files.add(file);
        File renamed = fileService.renameFile(files.get(0), "newTest.txt");
 */
        dirService.init();  //Слабое место. Неявно, что нужно вызывать.
        fileService.fillingArray(dirService.getFirstDirectory());
        fileService.movingFiles(userFilesList.getList(), dirService.getFirstDirectory(), dirService.getSecondDirectory());

    }
}
