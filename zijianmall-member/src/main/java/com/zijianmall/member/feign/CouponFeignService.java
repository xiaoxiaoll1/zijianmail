package com.zijianmall.member.feign;

import com.zijianmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaozj
 */
@FeignClient("zijianmall-coupon")
public interface CouponFeignService {

    @RequestMapping("/coupen/coupon/member/list")
    public R memberList();
}
