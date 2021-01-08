package com.zijianmall.ware.service.impl;

import com.zijianmall.common.constant.PurchaseDetailStatusEnum;
import com.zijianmall.common.constant.PurchaseStatusEnum;
import com.zijianmall.ware.entity.PurchaseDetailEntity;
import com.zijianmall.ware.exception.WareException;
import com.zijianmall.ware.service.PurchaseDetailService;
import com.zijianmall.ware.service.WareSkuService;
import com.zijianmall.ware.vo.MergeVo;
import com.zijianmall.ware.vo.PurchaseDoneVo;
import com.zijianmall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.ware.dao.PurchaseDao;
import com.zijianmall.ware.entity.PurchaseEntity;
import com.zijianmall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author xiaozj
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivePurchase(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<>();
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params), wrapper
                .eq("status", PurchaseStatusEnum.CREATED.getCode()).or()
                .eq("status", PurchaseStatusEnum.ASSIGNED.getCode()));

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        List<Long> items = mergeVo.getItems();
        Long purchaseId = mergeVo.getPurchaseId();
        if (mergeVo.getPurchaseId() == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        else {
            items.stream().forEach(item -> {
            List<PurchaseDetailEntity> details = purchaseDetailService.listByPurchaseId(item);
            details.stream().forEach(detail -> {
                if (detail.getStatus() != PurchaseDetailStatusEnum.CREATED.getCode() ||
                detail.getStatus() != PurchaseDetailStatusEnum.ASSIGNED.getCode()) {
                    throw new WareException();
                }
            });
        });
        }

        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> detailEntities = items.stream().map(item -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setId(item);
            purchaseDetailEntity.setStatus(PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(detailEntities);

    }

    @Override
    public void receivePurchase(List<Long> ids) {
        List<PurchaseEntity> entities = ids.stream().map(this::getById).filter(item -> {
            Integer status = item.getStatus();
            return status == PurchaseStatusEnum.CREATED.getCode() ||
                    status == PurchaseStatusEnum.ASSIGNED.getCode();
        }).map(item -> {
            item.setStatus(PurchaseStatusEnum.RECEIVED.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        this.updateBatchById(entities);

        entities.stream().forEach(entity -> {
            List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.listByPurchaseId(entity.getId());
            List<PurchaseDetailEntity> entitiesToUpdate = purchaseDetailEntities.stream().map(purchaseDetailEntity -> {
                PurchaseDetailEntity entityToUpdate = new PurchaseDetailEntity();
                entityToUpdate.setId(purchaseDetailEntity.getId());
                entityToUpdate.setStatus(PurchaseDetailStatusEnum.PURCHASING.getCode());
                return entityToUpdate;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(entitiesToUpdate);
        });
    }

    @Override
    public void finishPurchase(PurchaseDoneVo purchaseDoneVo) {
        Long id = purchaseDoneVo.getId();

        List<PurchaseItemDoneVo> items = purchaseDoneVo.getItems();
        AtomicBoolean purFlag = new AtomicBoolean(true);
        List<PurchaseDetailEntity> details = items.stream().map(item -> {
            PurchaseDetailEntity entity = new PurchaseDetailEntity();
            if (item.getStatus() == PurchaseDetailStatusEnum.PURCHASE_FAIL.getCode()) {
                entity.setStatus(item.getStatus());
                purFlag.set(false);
            } else {
                entity.setStatus(PurchaseDetailStatusEnum.FINISHED.getCode());
                PurchaseDetailEntity detail = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(detail);
            }
            entity.setId(item.getItemId());
            return entity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(details);

        boolean flag = purFlag.get();
        PurchaseEntity entity = new PurchaseEntity();
        entity.setId(id);
        entity.setStatus(flag ? PurchaseStatusEnum.FINISHED.getCode() :
                PurchaseStatusEnum.HAS_ERROR.getCode());
        entity.setUpdateTime(new Date());
        this.updateById(entity);
    }

}