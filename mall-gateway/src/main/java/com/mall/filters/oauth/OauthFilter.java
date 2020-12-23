package com.mall.filters.oauth;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.systemservice.rpc.oauth.OAuth2Rpc;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2AccessTokenRespDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import static cn.iocoder.common.framework.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.isAlreadyRouted;

/**
 * @author Mr.z
 * @since 2020/12/21-9:35
 */
@Component
public class OauthFilter implements GlobalFilter, Ordered {

    @Value("${mall.gateway.whiteList:/user-api/passport/send-sms-code,/user-api/passport/login-by-sms}")
    private String whiteList;

    @Resource
    private OAuth2Rpc oAuth2Rpc;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        String uri = serverHttpRequest.getURI().getPath();
        // 白名单直接通过
        if (whiteList.contains(uri)) {
            return chain.filter(exchange);
        }

        if (isAlreadyRouted(exchange)) {
            return chain.filter(exchange);
        }

        String authorization = serverHttpRequest.getHeaders().getFirst("Authorization");
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf("Bearer ");
        if (index == -1) { // 未找到
            return null;
        }

        String accessToken = authorization.substring(index + 7).trim();
        Integer userId;
        Integer userType;
        if (!StringUtils.isEmpty(accessToken)) {
            CommonResult<OAuth2AccessTokenRespDTO> checkAccessTokenResult = oAuth2Rpc.checkAccessToken(accessToken);
            checkAccessTokenResult.checkError();
            // 获得用户编号
            userId = checkAccessTokenResult.getData().getUserId();
            userType = checkAccessTokenResult.getData().getUserType();
        } else {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(serverHttpResponse);
        }
        if (userId == null || userType == null) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return getVoidMono(serverHttpResponse);
        }
        ServerHttpRequest mutableReq = serverHttpRequest.mutate().header("userId", userId.toString()).
                header("userType", String.valueOf(userType)).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();

        return chain.filter(mutableExchange);
    }


    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        CommonResult<Void> responseResult = CommonResult.error(BAD_REQUEST.getCode(), BAD_REQUEST.getMessage());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(responseResult).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
