package ru.palankar.antiviruscontrolscript.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface DirectoryService {

    /**
     * Инициалиизация директорий из .properties файла
     * Файл обязательно должен лежать в одной директории со скриптом
     * @param   properties  путь к файлу с указаниями директорий
     */
    void init(String properties);

    /**
     * Получить путь стартовой директории
     * @return  стартовая директория в формате Path
     */
    Path getFirstDirectory();

    /**
     * Задать путь стартовой директории
     * @param   firstDirectory  путь стартовой директории
     */
    void setFirstDirectory(String firstDirectory);

    /**
     * Получить путь директории с антивирусом
     * @return  директория с анитвирусом в формате Path
     */
    Path getSecondDirectory();

    /**
     * Задать путь директории с антивирусом
     * @param   secondDirectory путь директории с антивирусом
     */
    void setSecondDirectory(String secondDirectory);

    /**
     * Получить путь финальной директории
     * @return  финальная директория в формате Path
     */
    Path getThirdDirectory();

    /**
     * Задать путь финальной директории
     * @param   thirdDirectory  путь финальной директории
     */
    void setThirdDirectory(String thirdDirectory);

    /**
     * Получить путь директории с ошибками
     * @return  директория с ошибками в формате Path
     */
    Path getErrorsDirectory();

    /**
     * Задать путь директории с ошибками
     * @param   secondDirectory путь директории с ошибками
     */
    void setErrorsDirectory(String secondDirectory);

    /**
     * Получить путь директории с kavshell
     * @return  директория с ошибками в формате Path
     */
    Path getKavshellDirectory();

    /**
     * Задать путь директории с kavshell
     * @param   kavshell путь директории с kavshell
     */
    void setKavshellDirectory(String kavshell);
}
