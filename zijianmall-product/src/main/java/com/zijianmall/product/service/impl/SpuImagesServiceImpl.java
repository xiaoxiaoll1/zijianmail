package com.zijianmall.product.service.impl;

import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;
import com.zijianmall.product.dao.SpuImagesDao;
import com.zijianmall.product.entity.SpuImagesEntity;
import com.zijianmall.product.service.SpuImagesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if (images != null && images.size() > 0) {
            List<SpuImagesEntity> spuImagesEntities = images.stream().map(img -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setImgUrl(img);
                spuImagesEntity.setSpuId(id);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            this.saveBatch(spuImagesEntities);
        }
    }

}