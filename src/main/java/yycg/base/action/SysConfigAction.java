package yycg.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import yycg.base.process.context.Config;
import yycg.base.vo.ActiveUser;
import yycg.util.ResourcesUtil;
import yycg.util.Vcom_3DES;

@Controller 
@RequestMapping("/sysconfig")
public class SysConfigAction {
	
	//生成加密串
	private String buildString(String userid,String pwd)throws Exception{
		//从配置文件获取密钥
		String key= ResourcesUtil.getValue(Config.SYSCONFIG, "deskey");
		//待加密串
		String oldstring = userid + "#" + pwd + "#" + System.currentTimeMillis();
		System.out.println("原始串="+oldstring);
		//进行3des加密，1表示加密，key：密钥
		Vcom_3DES tempDesEn = new Vcom_3DES(1, oldstring,
				key);
		//加密后的串
		String strTemp = tempDesEn.Vcom3DESChiper();
		return strTemp;
	}

	//sysmanagerurl_modulelist=http://localhost:8080/sysmanager/module/show.do?loginkeyString=
	/**
	 * 模块操作管理
	 */
	@RequestMapping("/modulelist")
	public String modulelist(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		
		ActiveUser activeUser = (ActiveUser)session.getAttribute(Config.ACTIVEUSER_KEY);
		String userid = activeUser.getUserid();
		//生成加密串
		String strTemp=buildString(userid,"test");

		//组织接口址
		//从配置文件获取接口址
		//获取模块管理的接口址
		String module_url = ResourcesUtil.getValue(Config.SYSCONFIG, "sysmanagerurl_modulelist");
		
		//将加密后的串追加到接口址后
		String url  = module_url+strTemp;
		return View.redirect(url);//转到第三方系统
	}
	
	//角色管理sysmanagerurl_rolelist=http://localhost:8080/sysmanager/role/show.do?loginkeyString=
	/**
	 * 角色管理
	 */
	@RequestMapping("/rolelist")
	public String rolelist(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		
		ActiveUser activeUser = (ActiveUser)session.getAttribute(Config.ACTIVEUSER_KEY);
		String userid = activeUser.getUserid();
		//生成加密串
		String strTemp=buildString(userid,"test");

		//组织接口址
		//从配置文件获取接口址
		//获取模块管理的接口址
		String rolelist_url = ResourcesUtil.getValue(Config.SYSCONFIG, "sysmanagerurl_rolelist");
		
		//将加密后的串追加到接口址后
		String url  = rolelist_url+strTemp;
		return View.redirect(url);//转到第三方系统
	}
	
	//系统参数配置sysconfigurl_basicinfo=http://localhost:8080/sysconfig/admin/findBasicinfoList.action?loginkeyString=
	/**
	 * 系统参数配置
	 */
	@RequestMapping("/basicinfo")
	public String basicinfo(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		
		ActiveUser activeUser = (ActiveUser)session.getAttribute(Config.ACTIVEUSER_KEY);
		String userid = activeUser.getUserid();
		//生成加密串
		String strTemp=buildString(userid,"test");

		//组织接口址
		//从配置文件获取接口址
		//获取模块管理的接口址
		String basicinfo_url = ResourcesUtil.getValue(Config.SYSCONFIG, "sysconfigurl_basicinfo");
		
		//将加密后的串追加到接口址后
		String url  = basicinfo_url+strTemp;
		return View.redirect(url);//转到第三方系统
	}
}