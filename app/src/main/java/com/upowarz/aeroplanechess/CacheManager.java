package com.upowarz.aeroplanechess;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private static Map<String, Object> data = new HashMap<>();

    public static void put(String key, Object value) {
        data.put(key, value);
    }

    public static void remove(String key) {
        data.remove(key);
    }

    public static <T> T get(String key, Class<T> clazz, T def) {
        Object re = data.get(key);
        return (re != null && clazz.isInstance(re)) ? clazz.cast(re) : def;
    }

    public static boolean hasData(String key) {
        return data.containsKey(key);
    }
}
