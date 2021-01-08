package com.zijianmall.common.constant;

/**
 * @author xiaozj
 */

public enum PurchaseDetailStatusEnum {
    CREATED(0, "新建"), ASSIGNED(1, "已分配"),
    PURCHASING(2, "正在采购"), FINISHED(3, "已完成"),
    PURCHASE_FAIL(4, "采购失败");


    private int code;
    private String message;

    private PurchaseDetailStatusEnum(int code, String message) {
        this.code =code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
}
