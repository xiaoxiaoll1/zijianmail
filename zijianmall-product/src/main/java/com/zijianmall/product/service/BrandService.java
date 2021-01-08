package com.zijianmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

