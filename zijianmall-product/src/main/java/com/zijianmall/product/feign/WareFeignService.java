package com.zijianmall.product.feign;

import com.zijianmall.common.utils.R;
import com.zijianmall.product.vo.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 远程调用ware微服务相关接口
 * @author xiaozj
 */
@FeignClient("zijianmall-ware")
public interface WareFeignService {

    @PostMapping("/ware/waresku/hasStock")
    R skuHasStock(@RequestBody List<Long> skuIds);

}
