package com.mall.handler.common;

import com.google.gson.Gson;

/**
 * @author Mr.z
 * @since 2020/12/22-11:01
 */
public class JsonHelper {

    /**
     * Json转换成对象
     *
     * @param json json string
     * @param <T>  对象类型
     * @return T
     */
    public static <T> T jsonToObject(String json, Class<T> t) {
        Gson gson = new Gson();
        return gson.fromJson(json, t);
    }

    /**
     * 对象转换成Json字符串
     *
     * @param object obj
     * @return json string
     */
    public static String objectToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
