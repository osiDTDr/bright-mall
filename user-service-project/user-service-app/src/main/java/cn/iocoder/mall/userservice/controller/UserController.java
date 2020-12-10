package cn.iocoder.mall.userservice.controller;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.userservice.rpc.sms.UserSmsCodeRpc;
import cn.iocoder.mall.userservice.rpc.sms.dto.UserSendSmsCodeReqDTO;
import cn.iocoder.mall.userservice.rpc.user.UserRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.common.framework.vo.CommonResult.success;

/**
 * @author Mr.z
 * @since 2020/12/10-14:05
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserSmsCodeRpc userSmsCodeRpc;

    @PostMapping("/sendSmsCode")
    public CommonResult<Boolean> sendSmsCode(@RequestBody UserSendSmsCodeReqDTO sendSmsCodeDTO){
        System.out.println("开始发送 验证码了");
        userSmsCodeRpc.sendSmsCode(sendSmsCodeDTO);
        return success(true);
    }
}
