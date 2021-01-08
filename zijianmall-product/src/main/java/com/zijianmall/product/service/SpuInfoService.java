package com.zijianmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.product.entity.SpuInfoEntity;
import com.zijianmall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-21 22:37:16
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity entity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

