package ru.palankar.antiviruscontrolscript.Service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class CommandServiceImpl implements CommandService {
    Logger logger = LogManager.getLogger(CommandServiceImpl.class);

    /**
     * Запускает команду под Windows
     * @param   command     команда для запуска
     */
    @Override
    public void runCmd(String command) {
        String winCmd = "cmd /c " + command;

        try {
            Runtime rnt = Runtime.getRuntime();
            Process proc = rnt.exec(winCmd);
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.error("Error starting command: " + winCmd);
        }

    }
}