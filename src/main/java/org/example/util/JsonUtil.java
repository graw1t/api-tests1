package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {

    public static <T> T getObjectFromString(String str, Class<T> tClass) {
        try {
            return new ObjectMapper().readValue(str, tClass);
        }
        catch (JsonProcessingException ignored) {}
        return null;
    }
}
