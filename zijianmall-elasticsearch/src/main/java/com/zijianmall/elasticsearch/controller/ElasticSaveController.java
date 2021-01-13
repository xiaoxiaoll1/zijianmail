package com.zijianmall.elasticsearch.controller;

import com.zijianmall.common.exception.ErrorCodeEnum;
import com.zijianmall.common.to.es.SkuEsModelTo;
import com.zijianmall.common.utils.R;
import com.zijianmall.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaozj
 */
@RequestMapping("search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public R productUp(@RequestBody List<SkuEsModelTo> skuEsModelTos) {
        boolean b = false;
        try {
            b = productService.productUp(skuEsModelTos);
        } catch (Exception e) {
            return R.error(ErrorCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), ErrorCodeEnum.PRODUCT_UP_EXCEPTION.getMessage());
        }
        return R.ok();
    }
}
