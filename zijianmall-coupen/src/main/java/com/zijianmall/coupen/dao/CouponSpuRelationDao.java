package com.zijianmall.coupen.dao;

import com.zijianmall.coupen.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 20:34:52
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
