package com.hramyko.finalapp.service.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hramyko.finalapp.persistence.entity.*;

import java.io.IOException;

public class JsonParser {

    public static String getInfoFromJson(String jsonString, String target) {
        String[] strings = jsonString.split(",|}");
        for (String s :
                strings) {
            if (s.contains(target)) {
                jsonString = s;
            }
        }
        strings = jsonString.split(":");
        jsonString = strings[1].replaceAll(" ", "").replaceAll("\"", "");
        if (jsonString.contains("\n")) {
            jsonString = jsonString.substring(0, jsonString.indexOf("\n") - 1);
        }
        return jsonString;
    }

    public static Object getObjectFromJson(String jsonString, String className) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object object = getObject(className);
        if (object == null) return null;
        try {
            object = objectMapper.readValue(jsonString, object.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static Object getObject(String name) {
        switch (name) {
            case "com.hramyko.finalapp.entity.Post":
                return new Post();
            case "com.hramyko.finalapp.entity.CommonUser":
                return new CommonUser();
            case "com.hramyko.finalapp.entity.Admin":
                return new Admin();
            case "com.hramyko.finalapp.entity.Trader":
                return new Trader();
            case "com.hramyko.finalapp.entity.GameObject":
                return new GameObject();
            case "com.hramyko.finalapp.entity.Comment":
                return new Comment();
            case "com.hramyko.finalapp.entity.Game":
                return new Game();
            default:
                return null;
        }
    }
}