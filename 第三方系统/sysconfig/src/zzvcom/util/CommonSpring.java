package zzvcom.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import zzvcom.sys.service.impl.RoleServiceImpl;
import zzvcom.sys.service.impl.UserServiceImpl;

public class CommonSpring {
public static ClassPathXmlApplicationContext ctx=ContentHelper.getContext();
	
	public static UserServiceImpl getUserService(){
		return (UserServiceImpl) ctx.getBean("userService");
	}
	public static RoleServiceImpl getRoleService(){
		return (RoleServiceImpl) ctx.getBean("roleService");
	}
}
