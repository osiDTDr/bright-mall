package cn.iocoder.mall.userservice.rpc.address;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.userservice.rpc.address.dto.UserAddressCreateReqDTO;
import cn.iocoder.mall.userservice.rpc.address.dto.UserAddressRespDTO;
import cn.iocoder.mall.userservice.rpc.address.dto.UserAddressUpdateReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收件地址 Rpc 接口
 */
@FeignClient("user-service")
public interface UserAddressRpc {

    /**
     * 创建用户收件地址
     *
     * @param createDTO 创建用户收件地址 DTO
     * @return 用户收件地址编号
     */
    @PostMapping("/createUserAddress")
    CommonResult<Integer> createUserAddress(UserAddressCreateReqDTO createDTO);

    /**
     * 更新用户收件地址
     *
     * @param updateDTO 更新用户收件地址 DTO
     */
    @PutMapping("/updateUserAddress")
    CommonResult<Boolean> updateUserAddress(UserAddressUpdateReqDTO updateDTO);

    /**
     * 删除用户收件地址
     *
     * @param userAddressId 用户收件地址编号
     */
    @DeleteMapping("/deleteUserAddress")
    CommonResult<Boolean> deleteUserAddress(Integer userAddressId);

    /**
     * 获得用户收件地址
     *
     * @param userAddressId 用户收件地址编号
     * @return 用户收件地址
     */
    @GetMapping("/getUserAddress")
    CommonResult<UserAddressRespDTO> getUserAddress(Integer userAddressId);

    /**
     * 获得用户收件地址列表
     *
     * @param userAddressIds 用户收件地址编号列表
     * @return 用户收件地址列表
     */
    @PostMapping("/listUserAddresses")
    CommonResult<List<UserAddressRespDTO>> listUserAddresses(List<Integer> userAddressIds);

    /**
     * 获取指定用户的收件地址列表
     *
     * @param userId 用户编号
     * @param type   地址类型
     * @return 收件地址列表
     */
    @GetMapping("/listUserAddresses/{userId}/{type}")
    CommonResult<List<UserAddressRespDTO>> listUserAddresses(@PathVariable Integer userId,@PathVariable Integer type);

}
