package com.softdev.system.util;

import lombok.Data;
import java.io.Serializable;

/**
 * common return
 * @author zhengkai.blog.csdn.net
 */
@Data
public class ReturnUtil implements Serializable {
	public static final long serialVersionUID = 42L;

	public static final int SUCCESS_CODE = 200;
	public static final int FAIL_CODE = 500;
	public static final int PAGE_CODE = 0;
	public static final String OBJECT_NOT_FOUND = "找不到该对象";
	public static final String OPERATION_SUCCESS = "操作成功";

	private int code;
	private String msg;
	private Object data;
	private int count;
	
	public ReturnUtil(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public ReturnUtil(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public ReturnUtil(Object data) {
		this.code = SUCCESS_CODE;
		this.data = data;
	}
	public ReturnUtil(String data) {
		this.code = SUCCESS_CODE;
		this.msg = data;
	}
	public ReturnUtil(Object data , int count ) {
		this.code = PAGE_CODE;
		this.data = data;
		this.count = count;
	}
	public static ReturnUtil PAGE(Object data , int count){
		return new ReturnUtil(data,count);
	}
	public static ReturnUtil DEFINE(int code, String msg , Object data){
		return new ReturnUtil(code,msg,data);
	}
	public static ReturnUtil PAGE(Object data , long count){
		return new ReturnUtil(data,Integer.parseInt(count+""));
	}
	public static ReturnUtil SUCCESS(){
		return new ReturnUtil(SUCCESS_CODE,OPERATION_SUCCESS);
	}
	public static ReturnUtil MESSAGE(String msg){
		return new ReturnUtil(SUCCESS_CODE,msg);
	}
	public static ReturnUtil NOT_FOUND(){
		return new ReturnUtil(ReturnUtil.FAIL_CODE,ReturnUtil.OBJECT_NOT_FOUND);
	}
	public static ReturnUtil DATA(Object data){
		return new ReturnUtil(SUCCESS_CODE,OPERATION_SUCCESS,data);
	}
	public static ReturnUtil ERROR(String msg){
		return new ReturnUtil(FAIL_CODE,msg);
	}
	public static ReturnUtil ERROR(){
		return new ReturnUtil(FAIL_CODE,OBJECT_NOT_FOUND);
	}
}
