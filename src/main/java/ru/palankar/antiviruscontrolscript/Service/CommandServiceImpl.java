package ru.palankar.antiviruscontrolscript.Service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class CommandServiceImpl implements CommandService {
    private Logger logger = LogManager.getLogger(CommandServiceImpl.class);

    /**
     * Запускает команду под Windows
     * @param   command     команда для запуска
     * @return  код возврата команды
     */
    @Override
    public int runCmd(String command) {
        String winCmd = "cmd /c " + command;

        int endInt = 0;
        try {
            Runtime rnt = Runtime.getRuntime();
            Process proc = rnt.exec(winCmd);
            //код возврата команды
            endInt = proc.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.error("Error starting command: " + winCmd);
        }
        return endInt;
    }
}
