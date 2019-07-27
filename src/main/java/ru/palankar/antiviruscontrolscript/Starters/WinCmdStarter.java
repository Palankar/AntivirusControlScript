package ru.palankar.antiviruscontrolscript.Starters;

import ru.palankar.antiviruscontrolscript.Service.FileService;
import ru.palankar.antiviruscontrolscript.Service.WincmdFileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WinCmdStarter {
    private static FileService fileService = new WincmdFileService();
    private static List<File> files = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        //СРАБОТАЛО!
        //String command = "cmd /c move C:\\B-repository\\awd.txt C:\\B-repository\\1";
        //Runtime.getRuntime().exec(command);

        File file = new File("C:\\Users\\palan\\Desktop\\C-repository\\test.txt");
        file.createNewFile();
        files.add(file);
        File renamed = fileService.renameFile(files.get(0), "newTest.txt");
    }
}
