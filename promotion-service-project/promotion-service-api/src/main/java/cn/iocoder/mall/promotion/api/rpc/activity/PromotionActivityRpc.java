package cn.iocoder.mall.promotion.api.rpc.activity;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.common.framework.vo.PageResult;
import cn.iocoder.mall.promotion.api.rpc.activity.dto.PromotionActivityListReqDTO;
import cn.iocoder.mall.promotion.api.rpc.activity.dto.PromotionActivityPageReqDTO;
import cn.iocoder.mall.promotion.api.rpc.activity.dto.PromotionActivityRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 促销活动 Rpc 接口
 */
@FeignClient(value = "promotion-service", contextId = "promotion-activity")
public interface PromotionActivityRpc {

    @PostMapping("/pagePromotionActivity")
    CommonResult<PageResult<PromotionActivityRespDTO>> pagePromotionActivity(@RequestBody PromotionActivityPageReqDTO pageReqDTO);

    @PostMapping("/listPromotionActivities")
    CommonResult<List<PromotionActivityRespDTO>> listPromotionActivities(@RequestBody PromotionActivityListReqDTO listReqDTO);

}
