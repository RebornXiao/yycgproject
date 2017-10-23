package zzvcom.sys.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.logicalcobwebs.proxool.Vcom_3DES;

import zzvcom.frame.beans.VcomFrameSettingImpl;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.util.GetPermission;

import com.opensymphony.xwork2.ModelDriven;

public class SysMngLoginAction extends BaseAction implements ModelDriven<VcomSysUser> {
	public static final Logger log = Logger.getLogger(SysMngLoginAction.class);
	VcomFrameSettingImpl frameindexpageimpl = new VcomFrameSettingImpl();
	Map<String, String> map = frameindexpageimpl.getFrameSetting();
	private String projecttitle;
	private String projecticon;
	private String sessionid;// 由教务管理平台跳转过关的加密串，其中包括用户名、时间戳，中间用#分隔
	private String jwglurl;

	public String getProjecttitle() {
		return map.get("project-name");
	}

	public String getProjecticon() {
		return map.get("index-icon");
	}

	public VcomSysUser getModel() {
		return user;
	}

	public String index() {
		request.setAttribute("message", "login");
		return SUCCESS;
	}

	public String quit() {
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("userInfo");
		request.getSession().removeAttribute("listOperate");
		request.getSession().removeAttribute("menuTree");
		request.setAttribute("message", "login");
		return SUCCESS;
	}

	public String login() {
		String message = "";
		System.out.println("sessionid=" + sessionid);
		if (sessionid == null || sessionid.equals("")) {
			return "error";
		}
		// 解析跳转过关的加密串
		Vcom_3DES tempDe = new Vcom_3DES(0, sessionid, "123456789012345678901234");
		String strTempDe = tempDe.Vcom3DESChiper();

		String[] sessionid_s = strTempDe.split("#");
		System.out.println("sessionidSSSS=" + strTempDe);
		try {
			// 判断加密串是否有效，时间戳必须在10秒以内
			System.out.println("aaaaaaaa=" + sessionid_s.length);
			System.out.println("sessionid_s[0]=" + sessionid_s[0]);
			System.out.println("bbbbbbbb=" + (System.currentTimeMillis() - Long.parseLong(sessionid_s[1])));
			if (sessionid_s.length < 2 ) {
				return "error";
			}
		} catch (Exception e) {
			return "error";
		}
		user.setUsercode(sessionid_s[0]);
		List<VcomSysUser> list = null;
		try {
			list = this.getUserService().queryByUsercode(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list == null)
			list = new ArrayList<VcomSysUser>();
		if (list.size() > 0) {
			user = list.get(0);
			request.getSession().setAttribute("userName", user.getUsername());

			String menuTree = GetPermission.getLoginUserPermission(user, this.getModuleService(), this.getRoleService());
			String menuTreeExt = GetPermission.getLoginUserPermissionExt(user, this.getModuleService(), this.getRoleService());
			request.getSession().setAttribute("menuTree", menuTree);
			request.getSession().setAttribute("menuTreeExt", menuTreeExt);
			request.getSession().setAttribute("userInfo", user);
			request.getSession().setAttribute("userName", user.getUsername());
			return SUCCESS;

		} else {
			message = "密码或用户名错误,请重新输入！";
			request.setAttribute("message", message);
			return "error";
		}
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getJwglurl() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// String path = request.getContextPath();
		String path = request.getScheme() + "://" + request.getServerName() + ":"
				+ (request.getServerPort() == 80 ? "" : request.getServerPort()) + "";
		return path + "/loginshow.action";
	}

	public void setJwglurl(String jwglurl) {
		this.jwglurl = jwglurl;
	}

}
