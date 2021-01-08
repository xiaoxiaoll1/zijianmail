package com.zijianmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.ware.entity.PurchaseEntity;
import com.zijianmall.ware.vo.MergeVo;
import com.zijianmall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 21:14:30
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void receivePurchase(List<Long> ids);

    void finishPurchase(PurchaseDoneVo purchaseDoneVo);
}

