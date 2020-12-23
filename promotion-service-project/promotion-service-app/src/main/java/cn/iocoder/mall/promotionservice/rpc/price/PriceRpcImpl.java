package cn.iocoder.mall.promotionservice.rpc.price;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.mall.promotion.api.rpc.price.PriceRpc;
import cn.iocoder.mall.promotion.api.rpc.price.dto.PriceProductCalcReqDTO;
import cn.iocoder.mall.promotion.api.rpc.price.dto.PriceProductCalcRespDTO;
import cn.iocoder.mall.promotionservice.manager.price.PriceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.common.framework.vo.CommonResult.success;

@RestController
public class PriceRpcImpl implements PriceRpc {

    @Autowired
    private PriceManager priceManager;

    @Override
    public CommonResult<PriceProductCalcRespDTO> calcProductPrice(PriceProductCalcReqDTO calcReqDTO) {
        return success(priceManager.calcProductPrice(calcReqDTO));
    }

}
