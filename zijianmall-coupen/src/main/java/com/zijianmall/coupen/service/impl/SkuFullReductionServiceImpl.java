package com.zijianmall.coupen.service.impl;

import com.zijianmall.common.to.MemberPrice;
import com.zijianmall.common.to.SkuReductionTo;
import com.zijianmall.coupen.entity.MemberPriceEntity;
import com.zijianmall.coupen.entity.SkuLadderEntity;
import com.zijianmall.coupen.service.MemberPriceService;
import com.zijianmall.coupen.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.Query;

import com.zijianmall.coupen.dao.SkuFullReductionDao;
import com.zijianmall.coupen.entity.SkuFullReductionEntity;
import com.zijianmall.coupen.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTo, skuLadderEntity);
        skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
        if (skuLadderEntity.getFullCount() > 0) {
            skuLadderService.save(skuLadderEntity);
        }
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
        skuFullReductionEntity.setAddOther(skuReductionTo.getCountStatus());
        this.save(skuFullReductionEntity);

        List<MemberPrice> memberPriceList = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> memberPriceEntities = memberPriceList.stream().map(memberPrice -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(memberPrice.getId());
            memberPriceEntity.setMemberLevelName(memberPrice.getName());
            memberPriceEntity.setMemberPrice(memberPrice.getPrice());
            return memberPriceEntity;
        }).filter(memberPriceEntity -> {
            return memberPriceEntity.getMemberPrice().compareTo(new BigDecimal("0")) == 1;
        }).collect(Collectors.toList());
        if (skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
            memberPriceService.saveBatch(memberPriceEntities);
        }
    }

}