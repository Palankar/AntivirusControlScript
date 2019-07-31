package ru.palankar.antiviruscontrolscript.Starters;

import ru.palankar.antiviruscontrolscript.Service.JSONService;
import ru.palankar.antiviruscontrolscript.Service.JSONServiceImpl;

import java.io.*;

public class JSONStarter {

    public static void main(String[] args) {
        JSONService service = new JSONServiceImpl();

        service.putParam(new File("D:\\A-repository (reserve)\\test.json")
                , "AntivirusScanResult"
                , "007");

        service.putParam(new File("D:\\A-repository (reserve)\\test.json")
                , "NewParam"
                , "(^^)");
    }
}
