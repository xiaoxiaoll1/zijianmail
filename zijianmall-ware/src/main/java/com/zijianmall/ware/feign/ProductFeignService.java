package com.zijianmall.ware.feign;

import com.zijianmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaozj
 */
@FeignClient("zijianmall-gateway")
public interface ProductFeignService {

    /**
     * 该方法通过网关来调用远程api
     * @param skuId
     * @return
     */
    @RequestMapping("/api/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
