package com.example.utils;

/**
 * Redis所有的Keys
 *
 * @Author: cxx
 * @Date: 2019/2/5 21:15
 */
public class RedisKeys {

    public static String getTokenKey(String key){

        return "token:"+ key;
    }
    public static String getCapatchaKey(String key){
        return "capatcha:"+ key;
    }
}
