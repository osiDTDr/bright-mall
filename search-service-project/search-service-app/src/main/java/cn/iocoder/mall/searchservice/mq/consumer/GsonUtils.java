package cn.iocoder.mall.searchservice.mq.consumer;

import cn.iocoder.mall.searchservice.mq.consumer.message.ProductUpdateMessage;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(GsonUtils.class);
    private static final Gson gson = new Gson();

    public static Object convertToObject(String value, Class clazz) {
        return gson.fromJson(value, clazz);
    }
}
