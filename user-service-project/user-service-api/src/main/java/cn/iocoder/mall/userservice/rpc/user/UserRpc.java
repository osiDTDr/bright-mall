package cn.iocoder.mall.userservice.rpc.user;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.common.framework.vo.PageResult;
import cn.iocoder.mall.userservice.rpc.user.dto.UserCreateReqDTO;
import cn.iocoder.mall.userservice.rpc.user.dto.UserPageReqDTO;
import cn.iocoder.mall.userservice.rpc.user.dto.UserRespDTO;
import cn.iocoder.mall.userservice.rpc.user.dto.UserUpdateReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient("user-service")
public interface UserRpc {

    /**
     * 获得用户
     *
     * @param userId 用户编号
     * @return 用户
     */
    @GetMapping("/getUser")
    CommonResult<UserRespDTO> getUser(Integer userId);

    /**
     * 基于手机号创建用户。
     * 如果用户已经存在，则直接进行返回
     *
     * @param createDTO 创建用户 DTO
     * @return 用户信息
     */
    @PostMapping("/createUserIfAbsent")
    CommonResult<UserRespDTO> createUserIfAbsent(UserCreateReqDTO createDTO);

    /**
     * 更新用户
     *
     * @param updateDTO 更新用户 DTO
     */
    @PutMapping("/updateUser")
    CommonResult<Boolean> updateUser(UserUpdateReqDTO updateDTO);

    /**
     * 获得用户列表
     *
     * @param userIds 用户编号列表
     * @return 用户列表
     */
    @PostMapping("/listUsers")
    CommonResult<List<UserRespDTO>> listUsers(List<Integer> userIds);

    /**
     * 获得用户分页
     *
     * @param pageDTO 用户分页查询
     * @return 用户分页结果
     */
    @PostMapping("/pageUser")
    CommonResult<PageResult<UserRespDTO>> pageUser(UserPageReqDTO pageDTO);

}
