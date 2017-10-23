package yycg.base.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.po.Sysuser;
import yycg.base.process.context.Config;
import yycg.base.service.UserManager;
import yycg.base.vo.ActiveUser;
import yycg.base.vo.Menu;
import yycg.base.vo.SysuserCustom;

/**
 * 系统首页
 * @author Thinkpad
 *
 */

@Controller
public class First {
	
	@Autowired
	UserManager userManager;//用户管理的服务接口
	
	@RequestMapping("/first")//访问http://localhost:8080/yycgproject/first.action
	public String first(Model model)throws Exception{
		
		List<SysuserCustom> list = userManager.findSysuserList(null);
		model.addAttribute("sysuser", list.get(0));
		 
		return "/base/first";//返回jsp页面
		
	}
	@RequestMapping("/welcome")
	public String welcome()throws Exception{
		
		return "/base/welcome";
	}
	
	/**
	 * 获取菜单菜单，并转成json
	 */
	@RequestMapping("/usermenu")
	public @ResponseBody Menu usermenu(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		ActiveUser activeUser =(ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		return activeUser.getMenu();
	}

}
