package com.zzvcom.sysmag.service;

import javax.servlet.http.HttpServletRequest;

import com.zzvcom.sysmag.pojo.User;

public interface SysLogService {

	public void insertLog(User loginUser, HttpServletRequest request, String moduleName, String operType, String concent);

	public String getIP(HttpServletRequest request);
}
