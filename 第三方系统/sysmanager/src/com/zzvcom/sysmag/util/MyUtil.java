package com.zzvcom.sysmag.util;


public class MyUtil {
	//获取系统配置文件的某项的值
	public static String getSysConfigValue(String key){
		return new PropertyManager("sysParams.properties").getPropertyStr(key);
	}
}
