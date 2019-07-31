package ru.palankar.antiviruscontrolscript.Service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JSONServiceImpl implements JSONService {
    Logger logger = LogManager.getLogger(JSONServiceImpl.class);

    /**
     * Добавляет в имеющийся JSON-файл параметр со значением, если такого
     * параметра до этого не было. Если был, то заменяет его значение на
     * переданное
     * @param   json    JSON-файл
     * @param   param   параметр
     * @param   value   значение
     */
    @Override
    public void putParam(File json, String param, String value) {
        JSONObject object = getObj(json.getPath());

        if (object.containsKey(param))
            object.replace(param, value);
        else
            object.put(param, value);

        try (FileWriter writer = new FileWriter(json.getPath())){
            writer.write(object.toJSONString());
        } catch (IOException e) {
            System.out.println("Ошибка");
        }
    }

    /**
     * Получает данные из json-файла в объект JSONObject
     * @param   path    путь json-файла
     * @return  объект JSONObject с данными из файла
     */
    @Override
    public JSONObject getObj(String path) {
        JSONParser parser = new JSONParser();
        JSONObject object = null;

        try (Reader reader = new FileReader(path)){

            object = (JSONObject) parser.parse(reader);

        } catch (IOException | ParseException e) {
            logger.error("Error reading json: " + path);
        }

        return object;
    }
}
