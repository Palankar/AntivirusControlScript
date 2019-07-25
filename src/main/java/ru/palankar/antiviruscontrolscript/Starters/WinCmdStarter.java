package ru.palankar.antiviruscontrolscript.Starters;

public class WinCmdStarter {

    public static void main(String[] args) throws Exception {
        //СРАБОТАЛО!
        String command = "cmd /c move C:\\B-repository\\awd.txt C:\\B-repository\\1";
        Runtime.getRuntime().exec(command);
    }
}
