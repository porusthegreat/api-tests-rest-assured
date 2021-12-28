package com.wtia.utils;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Collection;

public class ResponseHelper {

    public static Object getResponseAsObject(String responseString, Class responseClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(responseString, responseClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Object getResponseAsObject(String responseString, Class<? extends Collection> type, Class<T> elementType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(responseString, mapper.getTypeFactory().constructCollectionType(type, elementType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
