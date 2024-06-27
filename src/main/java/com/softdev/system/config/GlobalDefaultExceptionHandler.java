package com.softdev.system.config;

import javax.servlet.http.HttpServletRequest;

import com.softdev.system.util.ReturnUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ReturnUtil defaultExceptionHandler(HttpServletRequest req, Exception e) {
		e.printStackTrace();
		return ReturnUtil.ERROR(e.getMessage());
	}
	
}
