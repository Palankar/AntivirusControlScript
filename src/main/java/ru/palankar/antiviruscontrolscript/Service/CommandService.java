package ru.palankar.antiviruscontrolscript.Service;

public interface CommandService {

    /**
     * Запускает команду через Runtime
     * @param   command     команда для запуска
     * @return  код возврата команды
     */
    int runCmd(String command);
}
