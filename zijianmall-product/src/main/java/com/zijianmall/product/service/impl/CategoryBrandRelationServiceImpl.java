package com.zijianmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zijianmall.product.dao.BrandDao;
import com.zijianmall.product.dao.CategoryDao;
import com.zijianmall.product.entity.BrandEntity;
import com.zijianmall.product.entity.CategoryEntity;
import com.zijianmall.product.vo.BrandVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.product.dao.CategoryBrandRelationDao;
import com.zijianmall.product.entity.CategoryBrandRelationEntity;
import com.zijianmall.product.service.CategoryBrandRelationService;


/**
 * @author xiaozj
 */
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBrandRelationDao categoryBrandRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);

    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(name);
        this.update(entity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setCatelogId(catId);
        entity.setCatelogName(name);
        this.update(entity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));

    }

    @Override
    public List<BrandEntity> findBrandByCatId(Long catId) {
        List<CategoryBrandRelationEntity> relationEntities = categoryBrandRelationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>()
                .eq("catelog_id", catId));
        List<BrandEntity> brandEntities = relationEntities.stream().map(relationEntity -> {
            BrandEntity brandEntity = brandDao.selectById(relationEntity.getBrandId());
            return brandEntity;
        }).collect(Collectors.toList());

        return brandEntities;
    }

}