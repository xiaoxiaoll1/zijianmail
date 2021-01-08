package com.zijianmall.common.constant;

/**
 * @author xiaozj
 */

public enum PurchaseStatusEnum {
    CREATED(0, "新建"), ASSIGNED(1, "已分配"),
    RECEIVED(2, "已领取"), FINISHED(3, "已完成"),
    HAS_ERROR(4, "有异常");


    private int code;
    private String message;

    private PurchaseStatusEnum(int code, String message) {
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
