package cn.iocoder.mall.systemservice.rpc.systemlog;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.common.framework.vo.PageResult;
import cn.iocoder.mall.systemservice.rpc.systemlog.dto.SystemExceptionLogCreateDTO;
import cn.iocoder.mall.systemservice.rpc.systemlog.dto.SystemExceptionLogPageDTO;
import cn.iocoder.mall.systemservice.rpc.systemlog.dto.SystemExceptionLogProcessDTO;
import cn.iocoder.mall.systemservice.rpc.systemlog.vo.SystemExceptionLogVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统异常日志 Rpc 接口
 */
@FeignClient(value = "system-service", contextId = "systemException")
public interface SystemExceptionLogRpc {

    /**
     * 创建系统异常日志
     *
     * @param createDTO 创建系统异常日志 DTO
     * @return 成功
     */
    @PostMapping("/createSystemExceptionLog")
    CommonResult<Boolean> createSystemExceptionLog(@RequestBody SystemExceptionLogCreateDTO createDTO);

    /**
     * 获得系统异常日志
     *
     * @param systemExceptionLogId 系统异常日志编号
     * @return 系统异常日志
     */
    @GetMapping("/getSystemExceptionLog/{systemExceptionLogId}")
    CommonResult<SystemExceptionLogVO> getSystemExceptionLog(@PathVariable Integer systemExceptionLogId);

    /**
     * 获得系统异常日志分页
     *
     * @param pageDTO 系统异常日志分页查询
     * @return 系统异常日志分页结果
     */
    @PostMapping("/pageSystemExceptionLog")
    CommonResult<PageResult<SystemExceptionLogVO>> pageSystemExceptionLog(@RequestBody SystemExceptionLogPageDTO pageDTO);

    /**
     * 处理系统异常日志，完成或者忽略
     *
     * @param processDTO 处理 DTO
     * @return 成功
     */
    @PostMapping("/processSystemExceptionLog")
    CommonResult<Boolean> processSystemExceptionLog(@RequestBody SystemExceptionLogProcessDTO processDTO);

}
