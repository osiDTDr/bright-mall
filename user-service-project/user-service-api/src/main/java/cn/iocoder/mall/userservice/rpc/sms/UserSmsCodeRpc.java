package cn.iocoder.mall.userservice.rpc.sms;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.userservice.rpc.sms.dto.UserSendSmsCodeReqDTO;
import cn.iocoder.mall.userservice.rpc.sms.dto.UserVerifySmsCodeReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户短信验证码 Rpc 接口
 */
@FeignClient("user-service")
public interface UserSmsCodeRpc {
    @PostMapping("/user/sendSmsCode")
    CommonResult<Boolean> sendSmsCode(@RequestBody UserSendSmsCodeReqDTO sendSmsCodeDTO);

    @PostMapping("/verifySmsCode")
    CommonResult<Boolean> verifySmsCode(UserVerifySmsCodeReqDTO verifySmsCodeDTO);

}
