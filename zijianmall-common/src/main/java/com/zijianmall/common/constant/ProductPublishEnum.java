package com.zijianmall.common.constant;

/**
 * @author xiaozj
 */

public enum ProductPublishEnum {
    SPU_CREATED(0, "新建"),
    SPU_UP(1, "上架"),
    SPU_DOWN(2, "下架");

    private int code;
    private String message;

    private ProductPublishEnum(int code, String message) {
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
