package com.zijianmall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * 合并vo
 * @author xiaozj
 */
@Data
public class MergeVo {

    private Long purchaseId;

    private List<Long> items;

}
