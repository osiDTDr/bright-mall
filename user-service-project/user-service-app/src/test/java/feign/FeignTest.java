package feign;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.systemservice.rpc.oauth.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.mall.userservice.UserServiceApplication;
import cn.iocoder.mall.userservice.feign.UserOAuth2Rpc;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserServiceApplication.class)
public class FeignTest {
    private static final Logger logger = LoggerFactory.getLogger(FeignTest.class);
    @Autowired
    private UserOAuth2Rpc userOAuth2Rpc;

    @Test
    public void testSystemService() throws InterruptedException {
        CommonResult<OAuth2AccessTokenRespDTO> yunai = userOAuth2Rpc.checkAccessToken("yunai");
        // 延长test结束时间,避免 feign http 请求中断
        Thread.sleep(10_000);
        logger.info("test finish");
    }
}
