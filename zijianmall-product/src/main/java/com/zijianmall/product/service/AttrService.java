package com.zijianmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.product.entity.AttrEntity;
import com.zijianmall.product.vo.AttrGroupRelationVo;
import com.zijianmall.product.vo.AttrRespVo;
import com.zijianmall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getAttrRelation(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getAttrNoRelation(Map<String, Object> params, Long attrgroupId);
}

