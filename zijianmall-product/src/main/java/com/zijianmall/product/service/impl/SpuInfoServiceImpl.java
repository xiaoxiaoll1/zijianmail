package com.zijianmall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.Function;
import com.zijianmall.common.constant.ProductPublishEnum;
import com.zijianmall.common.to.SkuReductionTo;
import com.zijianmall.common.to.SpuBoundsTo;
import com.zijianmall.common.to.es.SkuEsModelTo;
import com.zijianmall.common.utils.R;
import com.zijianmall.product.entity.*;
import com.zijianmall.product.feign.CouponFeignService;
import com.zijianmall.product.feign.ElasticFeignService;
import com.zijianmall.product.feign.WareFeignService;
import com.zijianmall.product.service.*;
import com.zijianmall.product.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;



@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private ElasticFeignService elasticFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        SpuInfoEntity entity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        // 调用save方法后，id会自动生成
        this.saveBaseSpuInfo(entity);
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(entity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveDesc(spuInfoDescEntity);
        List<String> images = vo.getImages();
        spuImagesService.saveImages(entity.getId(), images);

        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(attr.getAttrId());
            productAttrValueEntity.setAttrValue(attr.getAttrValues());
            productAttrValueEntity.setQuickShow(attr.getShowDesc());
            productAttrValueEntity.setSpuId(entity.getId());
            AttrEntity attrEntity = attrService.getById(attr.getAttrId());
            productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            return productAttrValueEntity;
        }).collect(Collectors.toList());

        productAttrValueService.saveBatchAttr(productAttrValueEntities);

        Bounds bounds = vo.getBounds();
        SpuBoundsTo boundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, boundsTo);
        boundsTo.setSpuId(entity.getId());

        R r = couponFeignService.saveSpuBounds(boundsTo);
        if (r.getCode() != 0) {
            log.error("调用远程服务失败");
        }

        List<Skus> skus = vo.getSkus();
        skus.stream().forEach(sku -> {
            List<Images> skuImages = sku.getImages();
            String defaultImg = "";
            if (skuImages != null && skuImages.size() > 0) {
                List<Images> defaultImages = skuImages.stream().filter(img -> {
                return img.getDefaultImg() == 1;
            }).collect(Collectors.toList());
                if (defaultImages != null && defaultImages.size() > 0) {
                    defaultImg = defaultImages.get(0).getImgUrl();
                }
            }
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(sku, skuInfoEntity);
            skuInfoEntity.setBrandId(entity.getBrandId());
            skuInfoEntity.setCatalogId(entity.getCatalogId());
            skuInfoEntity.setSaleCount(0L);
            skuInfoEntity.setSpuId(entity.getId());
            skuInfoEntity.setSkuDefaultImg(defaultImg);
            skuInfoService.saveSkuInfo(skuInfoEntity);

            Long skuId = skuInfoEntity.getSkuId();
            List<SkuImagesEntity> skuImagesEntities = sku.getImages().stream().map(img -> {
                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                skuImagesEntity.setSkuId(skuId);
                skuImagesEntity.setImgUrl(img.getImgUrl());
                skuImagesEntity.setDefaultImg(img.getDefaultImg());
                return skuImagesEntity;
            }).filter(img -> {
                return StringUtils.isNotEmpty(img.getImgUrl());
            }).collect(Collectors.toList());
            skuImagesService.saveBatch(skuImagesEntities);

            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = sku.getAttr().stream().map(attr -> {
                SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                skuSaleAttrValueEntity.setSkuId(skuId);
                BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                return skuSaleAttrValueEntity;
            }).collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

            SkuReductionTo skuReductionTo = new SkuReductionTo();
            BeanUtils.copyProperties(sku, skuReductionTo);
            skuReductionTo.setSkuId(skuId);
            if (skuReductionTo.getFullCount() > 0 ||
                    skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                couponFeignService.saveSkuReduction(skuReductionTo);
            }
        });



    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity entity) {
        this.baseMapper.insert(entity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            // 这里的and相当于括号。
            wrapper.and(w -> {
                w.eq("id", key).or().like("spu_name", key);
            });
        }
        String status = (String) params.get("status");
        if (StringUtils.isNotBlank(status) && !"0".equalsIgnoreCase(status)) {
            wrapper.eq("publish_status", status);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.isNotBlank(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.isNotBlank(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            wrapper.eq("catalog_id", catelogId);
        }
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void spuUp(Long spuId) {
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListForSpu(spuId);
        List<Long> attrIds = baseAttrs.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        List<Long> attrs = attrService.selectSearchAttr(attrIds);
        Set<Long> set = new HashSet<>(attrs);
        List<SkuEsModelTo.Attrs> attrsList = baseAttrs.stream().filter(attr -> {
            return set.contains(attr.getAttrId());
        }).map(item -> {
            SkuEsModelTo.Attrs skuAttrs = new SkuEsModelTo.Attrs();
            BeanUtils.copyProperties(item, skuAttrs);
            return skuAttrs;
        }).collect(Collectors.toList());

        List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);
        // 有两种方式可以省略map中的参数，第一种：map中遍历的数据为此时的入参
        // 第二种：map中遍历的数据调用的是他的实体类的方法（基本方法）
        List<Long> skuIds = skus.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        Map<Long, Boolean> map = null;
        try {
            R r = wareFeignService.skuHasStock(skuIds);
            // JSON的反序列化默认不能生成泛型，如LiRst<XXX>，这是就需要Typeeference
            List<SkuHasStockVo> vos = r.getData(new TypeReference<List<SkuHasStockVo>>(){});
            map = vos.stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::isHasStock));
        } catch (Exception e) {
            log.error("远程服务feign调用失败");
        }

        Map<Long, Boolean> finalMap = map;
        List<SkuEsModelTo> esModelTos = skus.stream().map(sku -> {
            SkuEsModelTo skuEsModelTo = new SkuEsModelTo();
            BeanUtils.copyProperties(sku, skuEsModelTo);
            skuEsModelTo.setSkuPrice(sku.getPrice());
            skuEsModelTo.setSkuImg(sku.getSkuDefaultImg());
            BrandEntity brand = brandService.getById(skuEsModelTo.getBrandId());
            skuEsModelTo.setBrandName(brand.getName());
            skuEsModelTo.setBrandImg(brand.getLogo());
            CategoryEntity catalog = categoryService.getById(skuEsModelTo.getCatalogId());
            skuEsModelTo.setCatalogName(catalog.getName());
            skuEsModelTo.setAttrs(attrsList);
            skuEsModelTo.setHotScore(0L);
            if (finalMap == null) {
             skuEsModelTo.setHasStock(true);
            } else {
                skuEsModelTo.setHasStock(finalMap.get(sku.getSkuId()));
            }

            return skuEsModelTo;

        }).collect(Collectors.toList());
        R r = elasticFeignService.productUp(esModelTos);
        if (r.getCode() == 0) {
            UpdateWrapper<SpuInfoEntity> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", spuId);
            wrapper.set("publish_status", ProductPublishEnum.SPU_UP.getCode());
            wrapper.set("update_time", new Date());
            this.update(wrapper);
        } else {
            //TODO 重复调用接口，接口幂等性
        }

    }

}