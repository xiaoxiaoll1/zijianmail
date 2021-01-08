package com.zijianmall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * spu品牌传输对象
 * @author xiaozj
 */
@Data
public class SpuBoundsTo {

    private Long spuId;

    private BigDecimal buyBounds;

    private BigDecimal growBounds;
}
