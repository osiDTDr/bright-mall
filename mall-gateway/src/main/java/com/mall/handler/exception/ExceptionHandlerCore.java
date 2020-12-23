package com.mall.handler.exception;

import com.mall.handler.common.SpringContextHolder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Mr.z
 * @since 2020/12/22-10:44
 */
@Configuration
public class ExceptionHandlerCore implements ApplicationRunner {

    /**
     * key是处理异常的类型
     * value是处理异常的方法
     */
    private HashMap<Class<? extends Throwable>, Node> exceptionHandlerMap;

    /**
     * 解析类上的注解
     * 将处理异常的方法注册到map中
     */
    private void register(Object exceptionAdvice) {
        Method[] methods = exceptionAdvice.getClass().getMethods();
        Arrays.stream(methods).forEach(method -> {
            ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
            if (Objects.isNull(exceptionHandler)) {
                return;
            }
            Arrays.asList(exceptionHandler.value()).forEach(a -> exceptionHandlerMap.put(a, new Node(method, exceptionAdvice)));
        });
    }

    /**
     * 根据异常对象获取解决异常的方法
     *
     * @param throwable 异常对象
     * @return handler method
     */
    private Node getHandlerExceptionMethodNode(Throwable throwable) {
        ArrayList<Class<?>> superClass = this.getSuperClass(throwable.getClass());
        for (Class<?> aClass : superClass) {
            Node handlerNode;
            if ((handlerNode = exceptionHandlerMap.get(aClass)) != null) {
                return handlerNode;
            }
        }
        return null;
    }

    @Override
    public void run(ApplicationArguments args) {
        exceptionHandlerMap = new HashMap<>();
        Map<String, Object> beans = SpringContextHolder.getBeansWithAnnotation(RestControllerAdvice.class);
        beans.keySet()
                .stream()
                .map(beans::get)
                .forEach(this::register);
    }

    /**
     * 对外暴露的处理异常的方法
     *
     * @param throwable 处理的异常
     * @return 调用异常后的返回值
     */
    public Object handlerException(ServerRequest request, Throwable throwable) {
        Node exceptionMethodNode = this.getHandlerExceptionMethodNode(throwable);
        if (Objects.isNull(exceptionMethodNode)) {
            throw new RuntimeException("No exception method");
        }

        Object returnResult = null;
        try {
            returnResult = exceptionMethodNode.method.invoke(exceptionMethodNode.thisObj, request, throwable);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnResult;
    }

    /**
     * 用于存放方法和方法所在的实例
     */
    private static class Node {
        Node(Method method, Object thisObj) {
            this.method = method;
            this.thisObj = thisObj;
        }

        Method method;
        Object thisObj;
    }


    /**
     * 获取该类的class以及所有父的class
     *
     * @param clazz this.class
     * @return list
     */
    public ArrayList<Class<?>> getSuperClass(Class<?> clazz) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        classes.add(clazz);
        Class<?> suCl = clazz.getSuperclass();
        while (suCl != null) {
            classes.add(suCl);
            suCl = suCl.getSuperclass();
        }
        return classes;
    }
}
