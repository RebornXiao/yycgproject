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

public class First extends BaseAction implements ModelDriven<VcomSysUser>{
	public static final Logger log = Logger.getLogger(First.class);
	VcomFrameSettingImpl frameindexpageimpl = new VcomFrameSettingImpl();
    Map<String, String> map = frameindexpageimpl.getFrameSetting();
    private String projecttitle;
    private String projecticon;
    private String sessionid;//由其它平台跳转过关的加密串，其中包括用户名、密码、时间戳，中间用||分隔
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
	
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	

}
