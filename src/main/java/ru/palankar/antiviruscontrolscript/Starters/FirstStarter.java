package ru.palankar.antiviruscontrolscript.Starters;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.palankar.antiviruscontrolscript.ScriptBody;

public class FirstStarter {
    private static final Logger logger = LogManager.getLogger(FirstStarter.class);

    public static void main(String[] args) {
        logger.info("Starting script initializing");
        ScriptBody scriptBody = new ScriptBody();

        // TODO: 24.07.2019 Логгировать надо иначе, по условиям
        logger.info("Files in first directory: ");
        scriptBody.getJSONList().stream().forEach(System.out::println);
    }

}
