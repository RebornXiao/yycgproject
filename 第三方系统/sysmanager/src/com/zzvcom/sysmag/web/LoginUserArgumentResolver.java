package com.zzvcom.sysmag.web;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.User;

public class LoginUserArgumentResolver implements WebArgumentResolver {

	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest)
			throws Exception {
				
		
		System.out.println("getParameterName=====: "+methodParameter.getParameterName());
		
		if ("loginUser".equals(methodParameter.getParameterName())) {
			Map userMap = (Map)webRequest.getAttribute("user", RequestAttributes.SCOPE_SESSION);
			Map roleMap = (Map)webRequest.getAttribute("role", RequestAttributes.SCOPE_SESSION);
			if(userMap!=null){
			String userName = (String)userMap.get("username");
			//System.out.println("username==="+userName);
			String roleId =null;
			User user = new User(userName);
			user.setTrueName((String)userMap.get("truename"));
			if(roleMap!=null){
				 roleId = (String)roleMap.get("roleid");
				 user.setRole(new Role(roleId));
				}else{
				 user.setRole(null);	
				}
			return user;
			}/*else{
				//这里暂时为无登录权限即可操作
				User user = new User("admin");
				 user.setRole(new Role("0"));
				 return user;
			}*/
		}
		if ("roleUser".equals(methodParameter.getParameterName())) {
			Map userMap = (Map)webRequest.getAttribute("user", RequestAttributes.SCOPE_SESSION);
			Map roleMap = (Map)webRequest.getAttribute("role", RequestAttributes.SCOPE_SESSION);
			if(userMap!=null){
			String userName = (String)userMap.get("username");
			//System.out.println("username==="+userName);
			String roleId =null;
			User user = new User(userName);
			user.setTrueName((String)userMap.get("truename"));
			if(roleMap!=null){
				 roleId = (String)roleMap.get("roleid");
				 user.setRole(new Role(roleId));
				}else{
				 user.setRole(new Role("0"));	
				}
			return user;
			}/*else{
				//这里暂时为无登录权限即可操作
				User user = new User("admin");
				 user.setRole(new Role("0"));
				 return user;
			}*/
		}
		return UNRESOLVED;
	}

}
