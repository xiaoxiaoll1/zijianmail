package com.zijianmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.zijianmall.product.entity.ProductAttrValueEntity;
import com.zijianmall.product.service.ProductAttrValueService;
import com.zijianmall.product.vo.AttrRespVo;
import com.zijianmall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zijianmall.product.entity.AttrEntity;
import com.zijianmall.product.service.AttrService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.common.utils.R;



/**
 * 商品属性
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 23:00:05
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @GetMapping("/base/listforspu/{spuId}")
    public R getAttrList(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> list = productAttrValueService.baseAttrListForSpu(spuId);
        return R.ok().put("data", list);
    }

    @PostMapping("/update/{spuId}")
    public R updateAttrList(@RequestBody List<ProductAttrValueEntity> entities,
                            @PathVariable("spuId") Long spuId) {
        productAttrValueService.updateAttrListForSpu(entities, spuId);
        return R.ok();
    }

    @GetMapping("/{type}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("type") String type) {
        PageUtils page = attrService.queryBaseAttrPage(params, catelogId, type);
        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
        AttrRespVo attrRespVo = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
