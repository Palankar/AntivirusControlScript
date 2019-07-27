package ru.palankar.antiviruscontrolscript.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Сервис взаимодействия с файлами
 */
public interface FileService {
    /**
     * Перемещает набор файлов из одной директории в другую
     * @param   files   исходная коллекция файлов
     * @param   from    начальная директория
     * @param   into    конечная директория
     */
    void movingFiles(List<File> files, Path from, Path into);

    /**
     * Сохранение отдельного файла в указанную директорию
     * @param   file    файл для сохранения
     * @param   into    директория сохранения
     * @return  File из новой директории
     */
    File savingFile(File file, Path into);

    /**
     * Переименовывает переданный в аргументы файл
     * @param   file    исходный файл
     * @param   newName новое имя файла
     * @return File с новым именем
     */
    File renameFile(File file, String newName);

    /**
     * Заменяет файл, уже имеющийся в коллекции на новый
     * @param   oldUserList имеющаяся коллекция файлов
     * @param   oldFile     старый файл, изначально находящийся в коллекции
     * @param   renamed     новый, переименованный файл, заменяющий старый в коллекции
     */
    void replaceFiles(List<File> oldUserList, File oldFile, File renamed);

    /**
     * Антивирусная проверка файлов
     * @param   files   коллекция, содержащая файлы на проверку антивирусом
     */
    void checkByAntivirus(Map<String, File> files);
}
