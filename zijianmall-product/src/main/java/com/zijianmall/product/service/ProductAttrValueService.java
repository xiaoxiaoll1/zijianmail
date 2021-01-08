package com.zijianmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatchAttr(List<ProductAttrValueEntity> productAttrValueEntities);

    List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId);

    void updateAttrListForSpu(List<ProductAttrValueEntity> entities, Long spuId);
}

