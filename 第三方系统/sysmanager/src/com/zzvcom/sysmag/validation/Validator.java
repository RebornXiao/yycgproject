package com.zzvcom.sysmag.validation;

/**
 * 表单校验器接口
 * @author Wang Xiaoming
 */
public interface Validator {
	/**
	 * 对象校验方法
	 * @param validateObj 校验对象
	 * @return Errors
	 */
	public Errors validate(Object validateObj);
}
