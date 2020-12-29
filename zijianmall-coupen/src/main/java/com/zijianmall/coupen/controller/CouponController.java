package com.zijianmall.coupen.controller;

import java.util.Arrays;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zijianmall.coupen.entity.CouponEntity;
import com.zijianmall.coupen.service.CouponService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.R;



/**
 * 优惠券信息
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 20:34:53
 */
@RefreshScope
@RestController
@RequestMapping("coupen/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Value("${coupon.name}")
    private String name;

    @RequestMapping("/test/config")
    public R testConfig(){
        return R.ok().put("config", name);
    }

    @RequestMapping("/member/list")
    public R memberList(){
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("lanlan");
        return R.ok().put("coupon", couponEntity.getCouponName());
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupen:coupon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupen:coupon:info")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("coupen:coupon:save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("coupen:coupon:update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("coupen:coupon:delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
