package com.softdev.system.config;

import com.softdev.system.exception.BusinessException;
import com.softdev.system.util.ReturnUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ReturnUtil<Void> handleBusinessException(BusinessException e) {
        log.warn("Business exception: {}", e.getMessage());
        return ReturnUtil.define(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ReturnUtil<Void> handleValidationException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("Validation error: {}", msg);
        return ReturnUtil.error(msg);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ReturnUtil<Void> handleBadCredentials(BadCredentialsException e) {
        return ReturnUtil.error("用户名或密码错误");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ReturnUtil<Void> handleAccessDenied(AccessDeniedException e) {
        return ReturnUtil.define(403, "无权限访问", null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnUtil<Void> handleException(Exception e) {
        log.error("Unexpected exception", e);
        return ReturnUtil.error("服务器内部错误");
    }
}
