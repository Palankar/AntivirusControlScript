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
    void moveFiles(List<File> files, Path from, Path into);

    /**
     * Сохранение отдельного файла в указанную директорию
     * @param   file    файл для сохранения
     * @param   into    директория сохранения
     * @return  File из новой директории
     */
    void saveFile(File file, Path into);

    /**
     * Переименовывает переданный в аргументы файл
     * @param   file    исходный файл
     * @param   newName новое имя файла
     */
    File renameFile(File file, String newName);

    /**
     * Заменяет файл, уже имеющийся в коллекции на новый
     * @param   oldFile     старый файл, изначально находящийся в коллекции
     * @param   renamed     новый, переименованный файл, заменяющий старый в коллекции
     */
    void updateFiles(File oldFile, File renamed);

    /**
     * Антивирусная проверка файлов
     * @param   files   коллекция, содержащая файлы на проверку антивирусом
     */
    void checkByAntivirus(Map<String, File> files);

    /**
     * Удаляет пекреданный в качестве аргумента файл
     * @param   file    удаляемый файл
     */
    void deleteFile(File file);

    /**
     * Перемещает набор JSON из одной директории в другую
     * @param   jsons   исходная коллекция json
     * @param   from    начальная директория
     * @param   into    конечная директория
     */
}
