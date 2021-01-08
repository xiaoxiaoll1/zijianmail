package com.zijianmall.ware.exception;


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
@RestControllerAdvice(basePackages = {"com.zijianmall.ware.controller"})
public class ZijianmallControllerWareAdvice {

    @ExceptionHandler(value = WareException.class)
    public R handleValidationException(MethodArgumentNotValidException e) {
        log.error("不能对于非创建和分配状体的采购单合并", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> map= new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(ErrorCodeEnum.PURCHASE_STATUS_EXCEPTION.getCode(),
                ErrorCodeEnum.PURCHASE_STATUS_EXCEPTION.getMessage()).put("data", map);
    }

}
