package com.zijianmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> asList);

    Long[] findCatelogPath(Long catelogId);

    void updateCascade(CategoryEntity category);
}

