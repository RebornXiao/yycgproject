package zzvcom.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.logicalcobwebs.proxool.Vcom_3DES;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import zzvcom.frame.beans.VcomFrameSettingImpl;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.util.GetPermission;
import zzvcom.sys.util.TaskMonitor;
import zzvcom.util.MD5;

public class LoginAction extends BaseAction implements ModelDriven<VcomSysUser>{
	public static final Logger log = Logger.getLogger(LoginAction.class);
	VcomFrameSettingImpl frameindexpageimpl = new VcomFrameSettingImpl();
    Map<String, String> map = frameindexpageimpl.getFrameSetting();
    private String projecttitle;
    private String projecticon;
    private String sessionid;//由外部系统跳转过关的加密串，其中包括用户名、密码、时间戳，中间用||分隔
    private String fromsysurl;
	public String getProjecttitle() {
		return map.get("project-name");
	}
	public String getProjecticon() {
		return map.get("index-icon");
	}
	public VcomSysUser getModel() {
		return user;
	}
	public String first(){
		HttpServletRequest request = ServletActionContext.getRequest();
		VcomSysUser usr=(VcomSysUser)request.getSession().getAttribute("userInfo");
		if(usr==null){
        	return ERROR;
        }else{
        	return SUCCESS;
        }
	}
	public String index(){
		request.setAttribute("message", "login");
		return SUCCESS;
	}
	public String quit(){
		//request.getSession().removeAttribute("user");
		//request.getSession().removeAttribute("userInfo");
		//request.getSession().removeAttribute("listOperate");
		//request.getSession().removeAttribute("menuTree");
		ActionContext.getContext().getSession().clear();
		request.setAttribute("message", "login");
		return SUCCESS;
	}
	public String login(){String message = "";
	MD5 md5 = new MD5();
	System.out.println("sessionid="+sessionid);
	if(sessionid==null||sessionid.equals("")){
		 return "error";
	}
	//解析跳转过关的加密串
	Vcom_3DES tempDe= new Vcom_3DES(0,sessionid,"123456789012345678901234");
	String strTempDe = tempDe.Vcom3DESChiper();
	
	String[] sessionid_s = strTempDe.split("#");
	System.out.println("sessionidSSSS="+strTempDe);
	try{
	//判断加密串是否有效，时间戳必须在10秒以内
		System.out.println("aaaaaaaa="+sessionid_s.length);
		System.out.println("bbbbbbbb="+(System.currentTimeMillis() - Long.parseLong(sessionid_s[2])));
		if(sessionid_s.length<3 || (System.currentTimeMillis() - Long.parseLong(sessionid_s[2]))/1000>10){
		return "error";
	}
	}catch(Exception e){
		return "error";
	}
	user.setUsercode(sessionid_s[0]);
	user.setPassword(sessionid_s[1]);
	/*
	String vcode = request.getParameter("vcode");
	if (request.getSession().getAttribute("validateCode") == null) {
		message = "校验码无效！";
		request.setAttribute("message", message);
		return "error";
	}
	String validateCode = request.getSession().getAttribute("validateCode")
			.toString();
	if (!vcode.equals(validateCode)) {
		message = "验证码错误,请重新输入！";
		request.setAttribute("message", message);
		return "error";
		*/
	//} else {暂时去掉验证码，因为需要由外部系统跳转
		String md5Code = md5.getMD5ofStr(user.getPassword()).trim();
		user.setPassword(md5Code);
		List<VcomSysUser> list=null;
		try{
			list = this.getUserService().queryByUser(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (list == null)
			list = new ArrayList<VcomSysUser>();
		if (list.size() > 0) {
			user = list.get(0);
			request.getSession().setAttribute("userName", user.getUsername());
			//String permission=user.getPermissions();
			if(user.getUsercode().equals("admin")){
				String menuTree=GetPermission.getLoginUserPermission(user,this.getModuleService(),this.getRoleService());
				String menuTreeExt=GetPermission.getLoginUserPermission(user,this.getModuleService(),this.getRoleService());
				request.getSession().setAttribute("menuTree", menuTree);
				request.getSession().setAttribute("menuTreeExt", menuTreeExt);
				request.getSession().setAttribute("userInfo", user);
				return SUCCESS;
			}else{
				/*if(null==permission||"".equals(permission)){
					message = "当前用户没有权限，请联系超级管理员！";
					request.setAttribute("message", message);
					return "error";
				}else{*/
					String menuTree=GetPermission.getLoginUserPermission(user,this.getModuleService(),this.getRoleService());
					String menuTreeExt=GetPermission.getLoginUserPermissionExt(user,this.getModuleService(),this.getRoleService());
					request.getSession().setAttribute("menuTree", menuTree);
                    request.getSession().setAttribute("menuTreeExt", menuTreeExt);
					request.getSession().setAttribute("userInfo", user);
					request.getSession().setAttribute("userName", user.getUsername());
					return SUCCESS;
				//}
			}
			
		} else {
			message = "密码或用户名错误,请重新输入！";
			request.setAttribute("message", message);
			return "error";
		}
	//}暂时去掉验证码，因为需要由外部系统跳转
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	public String getFromsysurl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//String path = request.getContextPath();
		String path = request.getScheme() + "://"
				+ request.getServerName() + ":" + (request.getServerPort()==80?"":request.getServerPort())
				+ "";
		return path+TaskMonitor.fromsysurl;
	}


}
