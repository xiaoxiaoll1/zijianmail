package com.zijianmall.order.dao;

import com.zijianmall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 21:01:35
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
