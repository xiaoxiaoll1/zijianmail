package com.zijianmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zijianmall.common.utils.R;
import com.zijianmall.ware.entity.PurchaseDetailEntity;
import com.zijianmall.ware.exception.PittyException;
import com.zijianmall.ware.feign.ProductFeignService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.PrinterIOException;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.ware.dao.WareSkuDao;
import com.zijianmall.ware.entity.WareSkuEntity;
import com.zijianmall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryWareSkuInfo(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        String wareId = (String) params.get("wareId");
        if (StringUtils.isNotBlank(wareId) && !"0".equalsIgnoreCase(wareId)) {
            wrapper.eq("ware_id", wareId);
        }
        String skuId = (String) params.get("skuId");
        if (StringUtils.isNotBlank(skuId) && !"0".equalsIgnoreCase(skuId)) {
            wrapper.eq("sku_id", skuId);
        }
        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params),wrapper);

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = {Exception.class}, noRollbackFor = {PittyException.class})
    @Override
    public void addStock(PurchaseDetailEntity detail) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id", detail.getSkuId())
                .eq("ware_id", detail.getWareId());

        List<WareSkuEntity> wareSkuEntities = this.list(queryWrapper);
        if (wareSkuEntities == null || wareSkuEntities.size() == 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            BeanUtils.copyProperties(detail, wareSkuEntity);
            wareSkuEntity.setStock(detail.getSkuNum());
            try {
                R info = productFeignService.info(detail.getSkuId());
                if (info.getCode() == 0) {
                    Map<String, Object> map = (Map<String, Object>) info.get("skuInfo");
                    wareSkuEntity.setSkuName((String) map.get("skuName"));
                }
            } catch (Exception e) {
            }
            this.save(wareSkuEntity);
        } else {
            wareSkuEntities.stream().forEach(entity -> {
                Integer stock = entity.getStock();
                this.update(entity, new UpdateWrapper<WareSkuEntity>()
                        .set("stock", stock + detail.getSkuNum())
                        .eq("sku_id", detail.getSkuId())
                        .eq("ware_id", detail.getWareId()));
            });
        }

    }

}