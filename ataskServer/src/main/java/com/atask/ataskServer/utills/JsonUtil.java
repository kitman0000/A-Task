package com.atask.ataskServer.utills;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by zhong on 2019-1-30.
 */
public class JsonUtil {
    public static String objectToJson(Object obj)
    {
        try {
            return (new ObjectMapper()).writeValueAsString(obj);
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public static Object jsonToObject(String json, Class toClass)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, toClass);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
