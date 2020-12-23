package cn.iocoder.mall.promotion.api.rpc.price;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.promotion.api.rpc.price.dto.PriceProductCalcReqDTO;
import cn.iocoder.mall.promotion.api.rpc.price.dto.PriceProductCalcRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 价格 Rpc 接口，提供价格计算的功能
 */
@FeignClient(value = "promotion-service", contextId = "price")
public interface PriceRpc {

    @PostMapping("/calcProductPrice")
    CommonResult<PriceProductCalcRespDTO> calcProductPrice(@RequestBody PriceProductCalcReqDTO calcReqDTO);

}
