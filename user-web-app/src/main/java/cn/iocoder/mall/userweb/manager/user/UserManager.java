package cn.iocoder.mall.userweb.manager.user;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.userservice.rpc.user.UserRpc;
import cn.iocoder.mall.userservice.rpc.user.dto.UserRespDTO;
import cn.iocoder.mall.userservice.rpc.user.dto.UserUpdateReqDTO;
import cn.iocoder.mall.userweb.controller.auth.user.vo.UserRespVO;
import cn.iocoder.mall.userweb.convert.user.UserConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    @Autowired
    private UserRpc userRpc;

    public UserRespVO getUser(Integer id) {
        CommonResult<UserRespDTO> userResult = userRpc.getUser(id);
        userResult.checkError();
        return UserConvert.INSTANCE.convert(userResult.getData());
    }

    public void updateUserAvatar(Integer userId, String avatar) {
        CommonResult<Boolean> updateUserResult = userRpc.updateUser(new UserUpdateReqDTO().setId(userId).setAvatar(avatar));
        updateUserResult.checkError();
    }

    public void updateUserNickname(Integer userId, String nickname) {
        CommonResult<Boolean> updateUserResult = userRpc.updateUser(new UserUpdateReqDTO().setId(userId).setNickname(nickname));
        updateUserResult.checkError();
    }

}
