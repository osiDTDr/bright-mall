package cn.iocoder.mall.systemservice.rpc.systemlog;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.common.framework.vo.PageResult;
import cn.iocoder.mall.systemservice.rpc.systemlog.dto.SystemAccessLogCreateDTO;
import cn.iocoder.mall.systemservice.rpc.systemlog.vo.SystemAccessLogPageDTO;
import cn.iocoder.mall.systemservice.rpc.systemlog.vo.SystemAccessLogVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
* 系统访问日志 Rpc 接口
*/
@FeignClient("system-service")
public interface SystemAccessLogRpc {

    /**
    * 创建系统访问日志
    *
    * @param createDTO 创建系统访问日志 DTO
    * @return 系统访问日志编号
    */
    @PostMapping("/createSystemAccessLog")
    CommonResult<Boolean> createSystemAccessLog(@RequestBody SystemAccessLogCreateDTO createDTO);

    /**
    * 获得系统访问日志分页
    *
    * @param pageDTO 系统访问日志分页查询
    * @return 系统访问日志分页结果
    */
    @PostMapping("/pageSystemAccessLog")
    CommonResult<PageResult<SystemAccessLogVO>> pageSystemAccessLog(@RequestBody SystemAccessLogPageDTO pageDTO);

}
