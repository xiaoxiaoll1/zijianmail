package com.zijianmall.product.vo;

import lombok.Data;

/**
 * @author xiaozj
 */
@Data
public class AttrRespVo extends AttrVo {

    private String catelogName;

    private String groupName;

    private Long[] catelogPath;
}
