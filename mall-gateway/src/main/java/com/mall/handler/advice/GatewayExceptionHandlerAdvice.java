package com.mall.handler.advice;

import cn.iocoder.common.framework.util.ExceptionUtil;
import cn.iocoder.common.framework.util.MallUtils;
import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.systemservice.rpc.systemlog.SystemExceptionLogRpc;
import cn.iocoder.mall.systemservice.rpc.systemlog.dto.SystemExceptionLogCreateDTO;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import feign.FeignException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.Date;
import java.util.Objects;

import static cn.iocoder.common.framework.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 全局异常处理器，将 Exception 翻译成 CommonResult + 对应的异常编号
 */
@RestControllerAdvice
public class GatewayExceptionHandlerAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private SystemExceptionLogRpc systemExceptionLogRpc;

    @ExceptionHandler(ResponseStatusException.class)
    public CommonResult<Void> handler(ResponseStatusException e) {
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), e.getReason());
    }

    @ExceptionHandler(ConnectException.class)
    public CommonResult<Void> handler(ConnectException e) {
        logger.error(Throwables.getStackTraceAsString(e));
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), "网络异常,请稍候再试!");
    }

    @ExceptionHandler(FeignException.class)
    public CommonResult<Object> handler(ServerRequest request, FeignException e) {
        logger.error(Throwables.getStackTraceAsString(e));
        this.createExceptionLog(request, e);
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), "API网关异常，请稍后再试！").setDetailMessage(e.getMessage());
    }

    @ExceptionHandler(FeignException.InternalServerError.class)
    public CommonResult<Object> handler(ServerRequest request, FeignException.InternalServerError e) {
        logger.error(Throwables.getStackTraceAsString(e));
        // 插入异常日志
        this.createExceptionLog(request, e);
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage()).setDetailMessage(e.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<Object> defaultExceptionHandler(ServerRequest req, Throwable ex) {
        logger.error("[defaultExceptionHandler]", ex);
        // 插入异常日志
        this.createExceptionLog(req, ex);
        // 返回 ERROR CommonResult
        return CommonResult.error(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage())
                .setDetailMessage(ExceptionUtil.getRootCauseMessage(ex));
    }

    public void createExceptionLog(ServerRequest req, Throwable e) {
        // 插入异常日志
        SystemExceptionLogCreateDTO exceptionLog = new SystemExceptionLogCreateDTO();
        try {
            // 初始化 exceptionLog
            initExceptionLog(exceptionLog, req, e);
            // 执行插入 exceptionLog
            createExceptionLog(exceptionLog);
        } catch (Throwable th) {
            logger.error("[createExceptionLog][插入访问日志({}) 发生异常({})", JSON.toJSONString(exceptionLog), ExceptionUtils.getRootCauseMessage(th));
        }
    }

    @Async
    public void createExceptionLog(SystemExceptionLogCreateDTO exceptionLog) {
        try {
            systemExceptionLogRpc.createSystemExceptionLog(exceptionLog);
        } catch (Throwable th) {
            logger.error("[addAccessLog][插入异常日志({}) 发生异常({})", JSON.toJSONString(exceptionLog), ExceptionUtils.getRootCauseMessage(th));
        }
    }

    private void initExceptionLog(SystemExceptionLogCreateDTO exceptionLog, ServerRequest serverRequest, Throwable e) {
        // 设置账号编号
        ServerHttpRequest request = serverRequest.exchange().getRequest();
        exceptionLog.setUserId(request.getHeaders().getFirst("userId") == null ? null : Integer.parseInt(Objects.requireNonNull(request.getHeaders().getFirst("userId"))));
        exceptionLog.setUserType(request.getHeaders().getFirst("userType") == null ? null : Integer.parseInt(Objects.requireNonNull(request.getHeaders().getFirst("userType"))));
        // 设置异常字段
        exceptionLog.setExceptionName(e.getClass().getName());
        exceptionLog.setExceptionMessage(ExceptionUtil.getMessage(e));
        exceptionLog.setExceptionRootCauseMessage(ExceptionUtil.getRootCauseMessage(e));
        exceptionLog.setExceptionStackTrace(ExceptionUtil.getStackTrace(e));
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        Assert.notEmpty(stackTraceElements, "异常 stackTraceElements 不能为空");
        StackTraceElement stackTraceElement = stackTraceElements[0];
        exceptionLog.setExceptionClassName(stackTraceElement.getClassName());
        exceptionLog.setExceptionFileName(stackTraceElement.getFileName());
        exceptionLog.setExceptionMethodName(stackTraceElement.getMethodName());
        exceptionLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
        // 设置其它字段
        exceptionLog.setTraceId(MallUtils.getTraceId())
                .setApplicationName(applicationName)
                .setUri(request.getURI().getPath())
                .setQueryString(request.getURI().getQuery() == null ? "" : request.getURI().getQuery())
                .setMethod(Objects.requireNonNull(request.getMethod()).toString())
                .setUserAgent(request.getHeaders().getFirst("User-Agent"))
                .setIp(request.getURI().getHost())
                .setExceptionTime(new Date());
    }

}
