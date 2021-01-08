package com.zijianmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.zijianmall.product.entity.AttrEntity;
import com.zijianmall.product.service.AttrAttrgroupRelationService;
import com.zijianmall.product.service.AttrService;
import com.zijianmall.product.service.CategoryService;
import com.zijianmall.product.vo.AttrGroupRelationVo;
import com.zijianmall.product.vo.AttrGroupWithAttrsVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zijianmall.product.entity.AttrGroupEntity;
import com.zijianmall.product.service.AttrGroupService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.R;



/**
 * 属性分组
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 23:00:05
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("categoryId") Long categoryId){
        PageUtils page = attrGroupService.queryPage(params, categoryId);
        return R.ok().put("page", page);
    }

    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data", vos);
    }

    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entityList = attrService.getAttrRelation(attrgroupId);
        return R.ok().put("data", entityList);

    }

    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long attrgroupId) {
        PageUtils page = attrService.getAttrNoRelation(params, attrgroupId);
        return R.ok().put("page", page);

    }

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        attrAttrgroupRelationService.saveBatchRelation(vos);
        return R.ok();
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
        attrService.deleteRelation(vos);
        return R.ok();

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
