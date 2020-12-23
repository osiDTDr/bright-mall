package com.mall.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.text.SimpleDateFormat;

/**
 * @author zhoutao
 * @Type RateLimiterFilter.java
 * @Desc
 * @date 2020/11/25 11:02
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class RateLimiterFilter implements Filter {

    private final RateLimiter rateLimiter = RateLimiter.create(10);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        rateLimiter.acquire();
        log.info("rate limiter end time ：" + dateFormat.format(System.currentTimeMillis()));
        return invoker.invoke(invocation);
    }
}
