package com.zijianmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.product.entity.AttrAttrgroupRelationEntity;
import com.zijianmall.product.vo.AttrGroupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatchRelation(List<AttrGroupRelationVo> vos);
}

