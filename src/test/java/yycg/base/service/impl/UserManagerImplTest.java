package yycg.base.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import yycg.base.service.UserManager;
import junit.framework.TestCase;

public class UserManagerImplTest extends TestCase {

	ApplicationContext applicationContext;
	
	protected void setUp() throws Exception {
		super.setUp();
		applicationContext = new ClassPathXmlApplicationContext(new String[]{
				"spring/applicationContext.xml",
				"spring/applicationContext-base-dao.xml",
				"spring/applicationContext-base-service.xml"
				
		});
	}

	public void testFindSysuserList() throws Exception{
		//从spring容器获取自定义的用户管理服务bean
		 UserManager userManager = (UserManager) applicationContext.getBean("userManager");
		 List list = userManager.findSysuserList(null);//调用服务方法获取用户列表
		 System.out.println(list.size());
	}

}
