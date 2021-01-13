package com.zijianmall.product.feign;

import com.zijianmall.common.to.es.SkuEsModelTo;
import com.zijianmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xiaozj
 */
@FeignClient("zijianmall-elasticsearch")
public interface ElasticFeignService {

    @PostMapping("/search/save/product")
    R productUp(@RequestBody List<SkuEsModelTo> skuEsModelTos);
}
