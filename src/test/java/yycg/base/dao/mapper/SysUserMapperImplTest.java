package yycg.base.dao.mapper;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import yycg.base.po.Sysuser;
import yycg.base.po.SysuserExample;
import yycg.util.MD5;
import yycg.util.UUIDBuild;

public class SysUserMapperImplTest extends TestCase {

	ApplicationContext applicationContext;
	
	
	
	
	protected void setUp() throws Exception {
		super.setUp();
		applicationContext = new ClassPathXmlApplicationContext(new String[]{
				"spring/applicationContext.xml",
				"spring/applicationContext-base-dao.xml"
				
		});
	}

	/**
	 * 测试自动mapper的查询方法
	 * @throws Exception
	 */
	public void testFindSysUserList() throws Exception{
		SysuserMapper sysUserMapper =(SysuserMapper) applicationContext.getBean("sysuserMapper");
		
		
		/**
		 * 根据主键查询记录，使用selectByPrimaryKey
		 */
		Sysuser sysuser = sysUserMapper.selectByPrimaryKey("41");
		System.out.println(sysuser.getUsername());
		
		/**
		 * 根据自定义查询条件查询列表
		 */
		SysuserExample sysuserExample = new SysuserExample();
		SysuserExample.Criteria criteria = sysuserExample.createCriteria();
		
		//根据用户类型groupid查询结果集
		//criteria.andGroupidEqualTo("3");//生成一个查询条件，groupid='3'
		criteria.andIdEqualTo("41");
		
		List<Sysuser> list = sysUserMapper.selectByExample(sysuserExample);
		System.out.println(list.size());
	}
	
	/**
	 * 测试添加
	 */
	public void testSysuserAdd() throws Exception{
		SysuserMapper sysUserMapper =(SysuserMapper) applicationContext.getBean("sysuserMapper");
		Sysuser sysuser = new Sysuser();
		sysuser.setId(UUIDBuild.getUUID());//主键
		sysuser.setUserid("test002");//账号
		sysuser.setUsername("测试账号002");//用户名称
		sysuser.setGroupid("3");//默认医院
		MD5 md5 = new MD5();
		sysuser.setPwd(md5.getMD5ofStr("111111"));
		sysuser.setUserstate("1");//用户状态为正常
		
		sysUserMapper.insert(sysuser);//执行插入
		
		//sysUserMapper.insertSelective(sysuser);
	}
	
	/**
	 * 测试更新
	 */
	public void testSysuserUpdate() throws Exception{
		SysuserMapper sysUserMapper =(SysuserMapper) applicationContext.getBean("sysuserMapper");
		//根据主键获取一条记录
		Sysuser sysuser =sysUserMapper.selectByPrimaryKey("0284f8be5ea7494a896afc9ba0a6da4d");
		MD5 md5 = new MD5();
		sysuser.setPwd(md5.getMD5ofStr("22222"));
		
		//sysUserMapper.updateByPrimaryKey(sysuser);//要求record对象里边必须有id
		Sysuser sysuser_u = new Sysuser();
		sysuser_u.setId("0284f8be5ea7494a896afc9ba0a6da4d");
		sysuser_u.setPwd(md5.getMD5ofStr("333333"));
		sysUserMapper.updateByPrimaryKeySelective(sysuser_u);
		

	}
	
	/**
	 * 测试删除
	 */
	
	public void testSysuserDelete() throws Exception{
		SysuserMapper sysUserMapper =(SysuserMapper) applicationContext.getBean("sysuserMapper");
		
		//sysUserMapper.deleteByPrimaryKey("0284f8be5ea7494a896afc9ba0a6da4d");
		
		SysuserExample sysuserExample = new SysuserExample();
		SysuserExample.Criteria criteria = sysuserExample.createCriteria();
		criteria.andUsernameLike("测试账号%");
		
		sysUserMapper.deleteByExample(sysuserExample);
		
	}

}
