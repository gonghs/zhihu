package com.maple.utils;

import com.google.gson.Gson;

/**
 * Created by TYZ034 on 2018/3/13.
 */
public class JsonUtils {
    public static String Object2String(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
