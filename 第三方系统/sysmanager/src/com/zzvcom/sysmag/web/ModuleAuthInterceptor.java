package com.zzvcom.sysmag.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.logicalcobwebs.proxool.Vcom_3DES;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.util.MyUtil;

/**
 * 登陆验证拦截器
 * @author mrt
 *
 */
public class ModuleAuthInterceptor extends HandlerInterceptorAdapter {
	private static final String LOGINUSER = "user";
	private static final String USERROLE = "role";
	private static final String LOGINKEY = "loginkeyString";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		ModelAndView mav = new ModelAndView("AuthError");
		//登陆加密串
		String loginkeyString = request.getParameter(LOGINKEY);
		
		Map user=(Map)request.getSession().getAttribute(LOGINUSER);
		
		if(user==null){
			if(loginkeyString == null || loginkeyString.equals("")){
				throw new ModelAndViewDefiningException(mav);
			}else{
				//获取加密key
				String key= MyUtil.getSysConfigValue("deskey");
				//解析跳转过关的加密串
				Vcom_3DES tempDe= new Vcom_3DES(0,loginkeyString,key);
				String strTempDe = tempDe.Vcom3DESChiper();
				
				String[] sessionid_s = strTempDe.split("#");
				System.out.println("loginkeyString="+strTempDe);
				//判断加密串是否有效，时间戳必须在10秒以内
				if(sessionid_s.length<3 || (System.currentTimeMillis() - Long.parseLong(sessionid_s[2]))/1000>10){
					throw new ModelAndViewDefiningException(mav);
				}
				
				
				Map userMap = new HashMap();
				userMap.put("username", "admin");
				Map roleMap = new HashMap();
				roleMap.put("roleid", "0");
				//roleMap.put("rolename", "");

				request.getSession().setAttribute("user", userMap);
				request.getSession().setAttribute("role", roleMap);
				
			}				
		}
		
/*		String moduleUrl = StringUtils.substringAfter(requestUrl, contextPath+"/");
		if (handler.getClass().isAssignableFrom(LoginController.class)) {
			return true;
		}*/
		/*
		if (!validateSession(request.getSession())) {
			//throw new ModelAndViewDefiningException(new ModelAndView("SessionOverTime"));
			response.sendRedirect(request.getContextPath()+"/jsp/SessionOverTime.jsp");
		}*/
		/*if (!validate(moduleUrl)) {
			throw new ModelAndViewDefiningException(mav);
		}*/
		return true;
		
	}
	
public static void main(String[] args){
		
		String key= MyUtil.getSysConfigValue("deskey");
		String oldstring = "test" + "#" + "test" + "#" + System.currentTimeMillis();
		org.logicalcobwebs.proxool.Vcom_3DES tempDesEn = new org.logicalcobwebs.proxool.Vcom_3DES(1, oldstring,
				key);
		String strTemp = tempDesEn.Vcom3DESChiper();
		System.out.println(strTemp);
		Vcom_3DES tempDe= new Vcom_3DES(0,strTemp,key);
		String strTempDe = tempDe.Vcom3DESChiper();
		System.out.println(strTempDe);
	}

}
