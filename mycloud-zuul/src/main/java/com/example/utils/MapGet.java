package com.example.utils;

import java.util.Map;

/**
 * User: lanxinghua
 * Date: 2019/4/12 14:19
 * Desc:
 */
public class MapGet {
    public static String getByKey(String key, Map<String, Object> params){
        if (params.get(key) == null){
            return null;
        }else {
            return params.get(key).toString();
        }
    }
}
