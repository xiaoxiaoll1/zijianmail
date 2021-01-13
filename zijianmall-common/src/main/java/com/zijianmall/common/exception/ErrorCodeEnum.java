package com.zijianmall.common.exception;

/**
 * 异常枚举类
 * @author xiaozj
 */

public enum ErrorCodeEnum {
    VALIDATION_EXCEPTION(10001, "数据校验失败"),
    UNKNOWN_EXCEPTION(10002, "未知异常"),
    PURCHASE_STATUS_EXCEPTION(11000, "采购单状态不正确"),
    REMOTE_CALL_TIME_OUT_EXCEPTION(12000, "远程服务调用超时"),
    PRODUCT_UP_EXCEPTION(13000, "商品上架异常")

    ;

    private Integer code;
    private String message;

    private ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
