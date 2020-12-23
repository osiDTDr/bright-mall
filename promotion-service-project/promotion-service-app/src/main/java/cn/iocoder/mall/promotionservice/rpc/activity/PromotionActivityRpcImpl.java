package cn.iocoder.mall.promotionservice.rpc.activity;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.common.framework.vo.PageResult;
import cn.iocoder.mall.promotion.api.rpc.activity.PromotionActivityRpc;
import cn.iocoder.mall.promotion.api.rpc.activity.dto.PromotionActivityListReqDTO;
import cn.iocoder.mall.promotion.api.rpc.activity.dto.PromotionActivityPageReqDTO;
import cn.iocoder.mall.promotion.api.rpc.activity.dto.PromotionActivityRespDTO;
import cn.iocoder.mall.promotionservice.manager.activity.PromotionActivityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.common.framework.vo.CommonResult.success;

@RestController
public class PromotionActivityRpcImpl implements PromotionActivityRpc {

    @Autowired
    private PromotionActivityManager promotionActivityManager;

    @Override
    public CommonResult<PageResult<PromotionActivityRespDTO>> pagePromotionActivity(PromotionActivityPageReqDTO pageReqDTO) {
        return success(promotionActivityManager.pagePromotionActivity(pageReqDTO));
    }

    @Override
    public CommonResult<List<PromotionActivityRespDTO>> listPromotionActivities(PromotionActivityListReqDTO listReqDTO) {
        return success(promotionActivityManager.listPromotionActivities(listReqDTO));
    }

}
