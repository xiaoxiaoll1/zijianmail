package com.zijianmall.ware.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.ware.dao.PurchaseDetailDao;
import com.zijianmall.ware.entity.PurchaseDetailEntity;
import com.zijianmall.ware.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                new QueryWrapper<PurchaseDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPurchaseDetail(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            wrapper.and(w -> {
                w.eq("purchase_id", key).or().eq("sku_id", key);
            });
        }
        String status = (String) params.get("status");
        if (StringUtils.isNotBlank(status) && !"0".equalsIgnoreCase(status)) {
            wrapper.eq("status", status);
        }
        String wareId = (String) params.get("wareId");
        if (StringUtils.isNotBlank(wareId) && !"0".equalsIgnoreCase(wareId)) {
            wrapper.eq("ware_id", wareId);
        }
        IPage<PurchaseDetailEntity> page = this.page(new Query<PurchaseDetailEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listByPurchaseId(Long id) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("purchase_id", id);
        List<PurchaseDetailEntity> list = this.list(wrapper);
        return list;
    }

}