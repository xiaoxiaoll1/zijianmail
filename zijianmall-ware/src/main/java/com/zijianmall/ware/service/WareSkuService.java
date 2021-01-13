package com.zijianmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.ware.entity.PurchaseDetailEntity;
import com.zijianmall.ware.entity.WareSkuEntity;
import com.zijianmall.ware.exception.PittyException;
import com.zijianmall.ware.vo.SkuHasStockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 21:14:30
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryWareSkuInfo(Map<String, Object> params);

    void addStock(PurchaseDetailEntity detail);

    List<SkuHasStockVo> skuHasStock(List<Long> skuIds);
}

