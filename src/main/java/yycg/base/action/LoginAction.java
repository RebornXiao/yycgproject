package yycg.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.UserManager;
import yycg.base.vo.ActiveUser;

/**
 * 系统登录
 * @author Thinkpad
 *
 */
@Controller
public class LoginAction {
	
	@Autowired
	UserManager userManager;
	
	/**
	 * 登录页面显示
	 */
	@RequestMapping("/login")
	public String login()throws Exception{
		
		return "/base/login";
	}
	/**
	 * 用户提交认证信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginsubmit")
	public @ResponseBody SubmitResultInfo loginsubmit(
			HttpServletRequest request,
			String userid,
			String pwd,
			String randomcode//验证码
			)throws Exception{
		
		//校验验证码
		//从session获取正确的验证码，和输入的对比
		HttpSession session = request.getSession();//获取session
		String randomcode_session = (String)session.getAttribute("validateCode");//从session获取正确的验证码
		//校验验证是否正确的
		if(!randomcode_session.equals(randomcode)){
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
		}
		
		//校验用户名和密码
		//用户身份信息，最终存入session
		ActiveUser activeUser = userManager.userlogin(userid, pwd);
		
		//将认证通过用户身份信息存入session，给用户办法通行证
				
		session.setAttribute(Config.ACTIVEUSER_KEY, activeUser);
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 107, new Object[]{activeUser.getUsername()}));
		
		
	}
	
	/**
	 * 用户退出
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();//获取session
		session.invalidate();//清除session
		//跳转到登录页面
		return View.redirect("first.action");
	}
	
}
