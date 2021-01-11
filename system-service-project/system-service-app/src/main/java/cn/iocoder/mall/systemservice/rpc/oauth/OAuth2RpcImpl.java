package cn.iocoder.mall.systemservice.rpc.oauth;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.systemservice.datasource.DatabaseContextHolder;
import cn.iocoder.mall.systemservice.datasource.DatabaseType;
import cn.iocoder.mall.systemservice.manager.oauth.OAuth2Manager;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2CreateAccessTokenReqDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2RefreshAccessTokenReqDTO;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2RemoveTokenByUserReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.common.framework.vo.CommonResult.success;

@RestController
public class OAuth2RpcImpl implements OAuth2Rpc {

    @Autowired
    private OAuth2Manager oauth2Manager;

    @Override
    public CommonResult<OAuth2AccessTokenRespDTO> createAccessToken(OAuth2CreateAccessTokenReqDTO createAccessTokenDTO) {
        return success(oauth2Manager.createAccessToken(createAccessTokenDTO));
    }

    /**
     * 查询以及检测 token
     *
     * @param accessToken token
     * @return token info
     */
    @Override
    public CommonResult<OAuth2AccessTokenRespDTO> checkAccessToken(String accessToken) {
        setDataSourceByEnvironment("1");
        return success(oauth2Manager.checkAccessToken(accessToken));
    }

    @Override
    public CommonResult<OAuth2AccessTokenRespDTO> refreshAccessToken(OAuth2RefreshAccessTokenReqDTO refreshAccessTokenDTO) {
        return success(oauth2Manager.refreshAccessToken(refreshAccessTokenDTO));
    }

    @Override
    public CommonResult<Boolean> removeToken(OAuth2RemoveTokenByUserReqDTO removeTokenDTO) {
        oauth2Manager.removeToken(removeTokenDTO);
        return success(true);
    }

    public void setDataSourceByEnvironment(String environment) {
        if (environment.equals(DatabaseType.FUNCTION.getValue())) {
            DatabaseContextHolder.setDatabaseType(DatabaseType.FUNCTION);
        }
        if (environment.equals(DatabaseType.REGRESSION.getValue())) {
            DatabaseContextHolder.setDatabaseType(DatabaseType.REGRESSION);
        }
    }
}
