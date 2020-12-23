package com.mall.handler.exception;

import com.mall.handler.common.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.z
 * @since 2020/12/22-9:05
 */
public class GatewayErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GatewayErrorWebExceptionHandler.class);

    @Resource
    private ExceptionHandlerCore handlerCore;

    public GatewayErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     */
//    @Override
//    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
//        int code = 500;
//        Throwable error = super.getError(request);
//        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
//            code = 404;
//        }
//        return response(code, this.buildMessage(request, error));
//    }
    @Override
    @SuppressWarnings(value = "unchecked")
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        //调用处理异常的方法，并将对象转换成map
        Object o = handlerCore.handlerException(request, error);
        String json = JsonHelper.objectToJson(o);
        return JsonHelper.jsonToObject(json, HashMap.class);
    }

    private Map<String, Object> response(int code, String buildMessage) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", buildMessage);
        map.put("data", null);
        return map;
    }

    private String buildMessage(ServerRequest request, Throwable error) {
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append(request.uri());
        message.append("]");
        if (error != null) {
            message.append(": ");
            message.append(error.getMessage());
        }
        return message.toString();
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     *
     * @param errorAttributes 异常属性
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return ((Double) errorAttributes.get("code")).intValue();
    }
}
