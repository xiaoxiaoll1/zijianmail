package com.zijianmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.product.dao.ProductAttrValueDao;
import com.zijianmall.product.entity.ProductAttrValueEntity;
import com.zijianmall.product.service.ProductAttrValueService;
import org.springframework.transaction.annotation.Transactional;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 为什么要采取这种写法，因为便于之后功能的拓展通过建立一个缓冲方法。
     * @param productAttrValueEntities
     */
    @Override
    public void saveBatchAttr(List<ProductAttrValueEntity> productAttrValueEntities) {
        this.saveBatch(productAttrValueEntities);
    }

    @Override
    public List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId) {
        List<ProductAttrValueEntity> list = this.list(new QueryWrapper<ProductAttrValueEntity>()
                .eq("spu_id", spuId));
        return list;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateAttrListForSpu(List<ProductAttrValueEntity> entities, Long spuId) {
        QueryWrapper<ProductAttrValueEntity> queryWrapper = new QueryWrapper<>();
        this.remove(queryWrapper.eq("spu_id", spuId));
        entities.stream().forEach(entity -> {
            entity.setSpuId(spuId);
        });
        this.saveBatch(entities);
    }

}