package com.zijianmall.product.exception;


import com.zijianmall.common.exception.ErrorCodeEnum;
import com.zijianmall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaozj
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.zijianmall.product.controller"})
public class ZijianmallControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidationException(MethodArgumentNotValidException e) {
        log.error("数据校验出现错误", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map= new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(ErrorCodeEnum.VALIDATION_EXCEPTION.getCode(),
                ErrorCodeEnum.VALIDATION_EXCEPTION.getMessage()).put("data", map);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleGenericException(Throwable e) {
        return R.error(ErrorCodeEnum.UNKNOWN_EXCEPTION.getCode(), ErrorCodeEnum.UNKNOWN_EXCEPTION.getMessage());
    }
}
