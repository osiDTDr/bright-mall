package cn.iocoder.mall.productservice.rpc.spu;

import cn.iocoder.common.framework.vo.CommonResult;
import cn.iocoder.common.framework.vo.PageResult;
import cn.iocoder.mall.productservice.rpc.spu.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * 商品 SPU Rpc 接口
 */
@FeignClient(value = "product-service", contextId = "productSpu")
public interface ProductSpuRpc {

    /**
     * 创建商品 SPU
     *
     * @param createDTO 创建商品 SPU DTO
     * @return 商品 SPU编号
     */
    @PostMapping("/createProductSpu")
    CommonResult<Integer> createProductSpu(@RequestBody ProductSpuAndSkuCreateReqDTO createDTO);

    /**
     * 更新商品 SPU
     *
     * @param updateDTO 更新商品 SPU DTO
     */
    @PostMapping("/updateProductSpu")
    CommonResult<Boolean> updateProductSpu(@RequestBody ProductSpuAndSkuUpdateReqDTO updateDTO);

    /**
     * 获得商品 SPU
     *
     * @param productSpuId 商品 SPU 编号
     * @return 商品 SPU
     */
    @GetMapping("/getProductSpu")
    CommonResult<ProductSpuRespDTO> getProductSpu(@RequestParam Integer productSpuId);

    /**
     * 获得商品 SPU列表
     *
     * @param productSpuIds 商品 SPU 编号列表
     * @return 商品 SPU 列表
     */
    @PostMapping("/listProductSpus")
    CommonResult<List<ProductSpuRespDTO>> listProductSpus(@RequestBody Collection<Integer> productSpuIds);

    /**
     * 获得商品 SPU 分页
     *
     * @param pageDTO 商品 SPU 分页查询
     * @return 商品 SPU 分页结果
     */
    @PostMapping("/pageProductSpu")
    CommonResult<PageResult<ProductSpuRespDTO>> pageProductSpu(@RequestBody ProductSpuPageReqDTO pageDTO);

    /**
     * 顺序获得商品 SPU 编号数组
     *
     * @param lastSpuId 最后一个商品 SPU 编号
     * @param limit     数量
     * @return 商品 SPU 编号数组
     */
    @GetMapping("/listProductSpuIds")
    CommonResult<List<Integer>> listProductSpuIds(@RequestParam("lastSpuId") Integer lastSpuId, @RequestParam("limit") Integer limit);

    @GetMapping("/getProductSpuDetail")
    CommonResult<ProductSpuDetailRespDTO> getProductSpuDetail(@RequestParam Integer productSpuId, @RequestParam("fields") Collection<String> fields);

}
