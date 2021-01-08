package com.zijianmall.ware.exception;

import com.zijianmall.common.exception.ErrorCodeEnum;

/**
 * 该类异常不重要，在事务方法中可不用回滚
 * @author xiaozj
 */
public class PittyException extends Exception {

    @Override
    public String getMessage() {
        return ErrorCodeEnum.REMOTE_CALL_TIME_OUT_EXCEPTION.getMessage();
    }
}
