package com.zijianmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijianmall.common.utils.PageUtils;
import com.zijianmall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author zijian
 * @email miraclexiao8@gmail.com
 * @date 2020-12-26 21:01:34
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

