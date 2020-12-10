package cn.iocoder.mall.systemservice.rpc.oauth;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2CreateAccessTokenReqDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2RefreshAccessTokenReqDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2RemoveTokenByUserReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "system-service")
public interface OAuth2Rpc {
    @RequestMapping(value = "/createAccessToken")
    CommonResult<OAuth2AccessTokenRespDTO> createAccessToken(OAuth2CreateAccessTokenReqDTO createAccessTokenDTO);

    @RequestMapping(value = "/checkAccessToken")
    CommonResult<OAuth2AccessTokenRespDTO> checkAccessToken(String accessToken);

    @RequestMapping(value = "/refreshAccessToken")
    CommonResult<OAuth2AccessTokenRespDTO> refreshAccessToken(OAuth2RefreshAccessTokenReqDTO refreshAccessTokenDTO);

    @RequestMapping(value = "/removeToken")
    CommonResult<Boolean> removeToken(OAuth2RemoveTokenByUserReqDTO removeTokenDTO);

}
