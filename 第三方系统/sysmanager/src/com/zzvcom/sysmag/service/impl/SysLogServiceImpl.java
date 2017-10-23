package com.zzvcom.sysmag.service.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.MessageInfo;

import com.zzvcom.sysmag.persistence.dao.BusinessDao;
import com.zzvcom.sysmag.persistence.dao.DeployNodeDao;
import com.zzvcom.sysmag.persistence.dao.RoleDao;
import com.zzvcom.sysmag.persistence.dao.SysWebserviceCfgDao;
import com.zzvcom.sysmag.persistence.dao.UserLogDao;
import com.zzvcom.sysmag.pojo.User;

@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	@Autowired
	private UserLogDao userLogDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private DeployNodeDao deployNodeDao;
	@Autowired
	private SysWebserviceCfgDao sysWebserviceCfgDao;
	
	public String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;

	}
	public void insertLog(User loginUser, HttpServletRequest request, String moduleName, String operType, String concent) {}
/*
 * 苗润土屏蔽，暂时不用
	public void insertLog(User loginUser, HttpServletRequest request, String moduleName, String operType, String concent) {
		HashMap map = new HashMap();
		map.put("username", loginUser.getUserName());
		map.put("truename", loginUser.getTrueName());
		map.put("clientip", getIP(request));
		map.put("modulename", moduleName);
		//区域
		if (moduleName.equals(MessageInfo.AREAMGMT)) {
			if ("".equals(operType) || null == operType) {
				map.put("opertype", MessageInfo.ADD);

			}
			else
			if (MessageInfo.DEL.equals(operType)) {
				map.put("opertype", MessageInfo.DEL);
			}
			else {
				map.put("opertype", MessageInfo.EDIT);
			}
			map.put("opercontent", "区域名称："+ concent);
		}
		//角色
		if (moduleName.equals(MessageInfo.ROLEMGMT)) {
			if ("".equals(operType) || null == operType) {
				map.put("opertype", MessageInfo.ADD);
			}
			else if (MessageInfo.DEL.equals(operType)) {
				map.put("opertype", MessageInfo.DEL);
			}
			else {
				map.put("opertype", MessageInfo.EDIT);
			}
			map.put("opercontent", "角色名称:"+concent);
		}
		//业务
		if (moduleName.equals(MessageInfo.BUSINESSMGMT)) {
			if ("".equals(operType) || null == operType) {
				map.put("opertype", MessageInfo.ADD);
			}
			else if (MessageInfo.DEL.equals(operType)) {
				map.put("opertype", MessageInfo.DEL);
			}
			else {
				map.put("opertype", MessageInfo.EDIT);
			}
			map.put("opercontent", "业务名称:"+concent);
		}
		//模块
		if (moduleName.equals(MessageInfo.MODULEMGMT)) {
			if ("add".equals(operType)) {
				map.put("opertype", MessageInfo.ADD);
			}
			else if (MessageInfo.DEL.equals(operType)) {
				map.put("opertype", MessageInfo.DEL);
			}
			else {
				map.put("opertype", MessageInfo.EDIT);
			}
			map.put("opercontent", "模块名称:"+concent);
		}
		//用户
		if (moduleName.equals(MessageInfo.USERMGMT)) {
			if ("add".equals(operType)) {
				map.put("opertype", MessageInfo.ADD);
			}
			else if (MessageInfo.DEL.equals(operType)) {
				map.put("opertype", MessageInfo.DEL);
			}
			else {
				map.put("opertype", MessageInfo.EDIT);
			}
			map.put("opercontent", "用户名称:"+concent);
		}
		//节点
		if (moduleName.equals(MessageInfo.DEPLOYNODEMGMT)) {
			if ("".equals(operType) || null == operType) {
				map.put("opertype", MessageInfo.ADD);
			}
			else if (MessageInfo.DEL.equals(operType)) {
				map.put("opertype", MessageInfo.DEL);
			}
			else {
				map.put("opertype", MessageInfo.EDIT);
			}
			map.put("opercontent", "节点名称:"+concent);
		}
		//操作
		if (moduleName.equals(MessageInfo.OPERATEMGMT)) {
			if ("add".equals(operType)) {
				map.put("opertype", MessageInfo.ADD);
			}
			else if (MessageInfo.DEL.equals(operType)) {
				map.put("opertype", MessageInfo.DEL);
			}
			else {
				map.put("opertype", MessageInfo.EDIT);
			}
			map.put("opercontent", "操作名称:"+concent);
			
		}
		
		// webservice管理
		if (moduleName.equals(MessageInfo.SYSWEBEMGMT)) {
			map.put("opertype",operType);
			
				map.put("opercontent", "webservice名称:"+concent);	
		
			
		}
		
		userLogDao.addUserLog(map);

	}
*/
}
