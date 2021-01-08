package com.zijianmall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xiaozj
 */
@Data
public class PurchaseDoneVo {

    @NotNull
    private Long id;

    private List<PurchaseItemDoneVo> items;
}
