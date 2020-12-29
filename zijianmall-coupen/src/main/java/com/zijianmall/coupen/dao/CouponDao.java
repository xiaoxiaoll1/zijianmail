package com.zijianmall.coupen.dao;

import com.zijianmall.coupen.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 20:34:53
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
