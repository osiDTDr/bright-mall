package cn.iocoder.mall.userservice.feign;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2CreateAccessTokenReqDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2RefreshAccessTokenReqDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2RemoveTokenByUserReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "system-service", contextId = "oauth2")
public interface UserOAuth2Rpc {

    @PostMapping(value = "/createAccessToken")
    CommonResult<OAuth2AccessTokenRespDTO> createAccessToken(@RequestBody OAuth2CreateAccessTokenReqDTO createAccessTokenDTO);

    /**
     * 查询以及检测 token
     *
     * @param accessToken token
     * @return token info
     */
    @GetMapping(value = "/checkAccessToken")
    CommonResult<OAuth2AccessTokenRespDTO> checkAccessToken(@RequestParam("accessToken") String accessToken);

    @PostMapping(value = "/refreshAccessToken")
    CommonResult<OAuth2AccessTokenRespDTO> refreshAccessToken(@RequestBody OAuth2RefreshAccessTokenReqDTO refreshAccessTokenDTO);

    @PutMapping(value = "/removeToken")
    CommonResult<Boolean> removeToken(@RequestBody OAuth2RemoveTokenByUserReqDTO removeTokenDTO);
}
