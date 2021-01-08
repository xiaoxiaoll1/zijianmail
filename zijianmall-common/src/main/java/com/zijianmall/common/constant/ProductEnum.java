package com.zijianmall.common.constant;

/**
 * @author xiaozj
 */

public enum ProductEnum {
    ATTR_TYPE_BASE(1, "基本属性"),
    ATTR_TYPE_SALE(0, "销售属性");

    private int code;
    private String message;

    private ProductEnum(int code, String message) {
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
