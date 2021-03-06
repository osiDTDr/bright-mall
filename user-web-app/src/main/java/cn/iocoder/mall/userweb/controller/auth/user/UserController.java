package cn.iocoder.mall.userweb.controller.auth.user;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.security.user.core.context.UserSecurityContextHolder;
import cn.iocoder.mall.userweb.controller.auth.user.vo.UserRespVO;
import cn.iocoder.mall.userweb.manager.user.UserManager;
import cn.iocoder.security.annotations.RequiresAuthenticate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static cn.iocoder.common.framework.vo.CommonResult.success;

@Api(tags = "用户信息 API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @ApiOperation(value = "用户信息")
    @GetMapping("/info")
    @RequiresAuthenticate
    public CommonResult<UserRespVO> getUserInfo(HttpServletRequest request) {
        UserRespVO user = userManager.getUser(Integer.parseInt(request.getHeader("userId")));
        return success(user);
    }

    @PostMapping("/update-avatar")
    @RequiresAuthenticate
    @ApiOperation(value = "更新头像")
    @ApiImplicitParam(name = "avatar", value = "头像", required = true, example = "http://www.iocoder.cn/xxx.png")
    public CommonResult<Boolean> updateUserAvatar(@RequestParam("avatar") String avatar) {
        userManager.updateUserAvatar(UserSecurityContextHolder.getUserId(), avatar);
        return success(true);
    }

    @PostMapping("/update-nickname")
    @RequiresAuthenticate
    @ApiOperation(value = "更新昵称")
    @ApiImplicitParam(name = "nickname", value = "昵称", required = true, example = "蠢艿艿")
    public CommonResult<Boolean> updateUserNickname(@RequestParam("nickname") String nickname) {
        userManager.updateUserNickname(UserSecurityContextHolder.getUserId(), nickname);
        return success(true);
    }

}
