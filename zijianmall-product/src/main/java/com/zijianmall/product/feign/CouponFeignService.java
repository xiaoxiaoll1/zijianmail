package com.zijianmall.product.feign;

import com.zijianmall.common.to.SkuReductionTo;
import com.zijianmall.common.to.SpuBoundsTo;
import com.zijianmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xiaozj
 */
@FeignClient(value = "zijianmall-coupon")
public interface CouponFeignService {

    /**
     * openFeign远程调用时，会将对象转为json数据，因此只要接受方法的参数对象包含相同的属性，就可以接收
     * @param boundsTo
     */
    @PostMapping("/coupen/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo boundsTo);

    @PostMapping("/coupen/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
