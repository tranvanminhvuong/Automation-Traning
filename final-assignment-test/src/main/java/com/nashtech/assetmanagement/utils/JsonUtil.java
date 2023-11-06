package com.nashtech.assetmanagement.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.nashtech.assetmanagement.exceptions.ParsingJsonException;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class JsonUtil {

    public static JsonObject readJsonFile(String filePath) {
        // Convert JSON File to Java JsonObject
        Type jsonObjectType = new TypeToken<JsonObject>() {
        }.getType();
        JsonObject jsonObject;
        Gson gson = new Gson();
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new FileReader(filePath));
            jsonObject = gson.fromJson(jsonReader, jsonObjectType);
        } catch (Exception e) {
            throw new ParsingJsonException("Can't parsing file to jsonObject", e);
        }
        return jsonObject;
    }
    public static Object[][] getData(String path) {
        JsonObject jsonObject;
        Object object = JsonUtil
                .readJsonFile(path);
        jsonObject = (JsonObject) object;

        Object[] data = new Object[1];
        HashMap<String, String> hashMap = new LinkedHashMap<>();
        Set<String> jsonObjKeys = jsonObject.keySet();
        for (String jsonObjKey : jsonObjKeys) {
            hashMap.put(jsonObjKey, jsonObject.get(jsonObjKey).getAsString());
        }
        data[0] = hashMap;
        return new Object[][] { data };
    }
}
