package zzvcom.sys.util.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.logicalcobwebs.proxool.Vcom_3DES;

import zzvcom.log.util.PropertyManager;
import zzvcom.sys.pojo.VcomSysUser;

import com.ewebeditor.admin.main_jsp;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 登陆验证拦截器
 * loginkeyString加密规则：
 * 使用 3DES加密算法
 * 加密串由用户账号、用户密码、时间戳(年月日时分秒)组成，中间用#分隔
 * 
 */


public class LoginInterceptor  extends AbstractInterceptor{
	
	private static final long serialVersionUID = -8148587933080816589L;
	private static final String NOSESSION = "sessionNotValid";
	private static final String LOGINKEY = "loginkeyString";
	private static final String LOGINUSER = "userInfo";
	
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		//登陆加密串
		String loginkeyString = request.getParameter(LOGINKEY);
		
		VcomSysUser user=(VcomSysUser)request.getSession().getAttribute(LOGINUSER);
		
		if(user==null){
			if(loginkeyString == null || loginkeyString.equals("")){
				return NOSESSION;
			}else{
				//获取加密key
				PropertyManager property = new PropertyManager("sysParams.properties");
				String key= property.getPropertyStr("deskey");
				//解析跳转过关的加密串
				Vcom_3DES tempDe= new Vcom_3DES(0,loginkeyString,key);
				String strTempDe = tempDe.Vcom3DESChiper();
				
				String[] sessionid_s = strTempDe.split("#");
				System.out.println("loginkeyString="+strTempDe);
				//判断加密串是否有效，时间戳必须在10秒以内
				if(sessionid_s.length<3 || (System.currentTimeMillis() - Long.parseLong(sessionid_s[2]))/1000>10){
					return NOSESSION;
				}
				user = new VcomSysUser();
				user.setUsercode(sessionid_s[0]);
				user.setPassword(sessionid_s[1]);
				request.getSession().setAttribute(LOGINUSER, user);
			}				
		}
		return invocation.invoke();//执行调用;
	}
	
	public static void main(String[] args){
		
		PropertyManager property = new PropertyManager("sysParams.properties");
		String key= property.getPropertyStr("deskey");
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
