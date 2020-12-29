package com.zijianmall.product.dao;

import com.zijianmall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    void selectList();
}
