package com.zzvcom.sysmag.validation;

import java.util.HashMap;

/**
 * 表单校验错误信息包装类
 * @author Wang Xiaoming
 */
public class Errors extends HashMap {
	/**
	 * 添加错误信息
	 * @param field 表单字段
	 * @param errorMessage 错误提示信息
	 */
	public void reject(String field, String errorMessage) {
		super.put(field, errorMessage);
	}
	
	/**
	 * 判断是否有错误信息
	 * @return true 有, false 无
	 */
	public boolean hasErrors() {
		return !(super.isEmpty());
	}
	
}
