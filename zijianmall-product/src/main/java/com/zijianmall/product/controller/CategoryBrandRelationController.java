package com.zijianmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijianmall.product.entity.BrandEntity;
import com.zijianmall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zijianmall.product.entity.CategoryBrandRelationEntity;
import com.zijianmall.product.service.CategoryBrandRelationService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 23:00:05
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * controller只做三件事：
     * 1.接受前端传来的参数，并进行校验（JSR303）等
     * 2.调用service执行业务逻辑
     * 3.接受service返回的实体对象，并根据需求封装成前端需要的Vo
     * @param catId
     * @return
     */
    @GetMapping("/brands/list")
    public R brandsList(@RequestParam(value = "catId", required = true) Long catId) {
        List<BrandEntity> brandEntities = categoryBrandRelationService.findBrandByCatId(catId);
        List<BrandVo> vos = brandEntities.stream().map(brandEntity -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(brandEntity.getBrandId());
            brandVo.setBrandName(brandEntity.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", vos);
    }

        /**
     * 列表
     */
    @GetMapping("/catelog/list")
    public R catelogList(@RequestParam("brandId") Long brandId){
        List<CategoryBrandRelationEntity> list = categoryBrandRelationService.list(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId)
        );
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
