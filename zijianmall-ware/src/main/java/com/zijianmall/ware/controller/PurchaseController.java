package com.zijianmall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.zijianmall.ware.vo.MergeVo;
import com.zijianmall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zijianmall.ware.entity.PurchaseEntity;
import com.zijianmall.ware.service.PurchaseService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.R;



/**
 * 采购信息
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 21:14:30
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/done")
    public R finishPurchase(@RequestBody PurchaseDoneVo purchaseDoneVo) {
        purchaseService.finishPurchase(purchaseDoneVo);
        return R.ok();
    }

    @PostMapping("/received")
    public R receivePurchase(@RequestBody List<Long> ids) {
        purchaseService.receivePurchase(ids);
        return R.ok();
    }

    @PostMapping("/merge")
    public R mergePurchase(@RequestBody MergeVo mergeVo) {
        purchaseService.mergePurchase(mergeVo);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryUnreceivePurchase(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);
        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){
        purchase.setUpdateTime(new Date());
        purchase.setCreateTime(new Date());
		purchaseService.save(purchase);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
