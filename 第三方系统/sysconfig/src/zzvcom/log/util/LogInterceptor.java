package zzvcom.log.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import zzvcom.log.pojo.VcomSysLog;
import zzvcom.sys.action.BaseAction;
import zzvcom.sys.pojo.VcomSysUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LogInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		String ret = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		Object action = invocation.getAction();
		boolean logable = false;
		BaseAction baseAction = null;
		if (action instanceof BaseAction) {
			logable = true;
			baseAction = (BaseAction) action;
		}
		try {
			ret = invocation.invoke();//执行调用
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.insertLogInfoForCatchEx(request, e);
			return ret;
		}
		//执行完毕写用户日志
		if (logable) {
			if(baseAction.isLog()){
				VcomSysLog log = new VcomSysLog();
				VcomSysUser user = (VcomSysUser) request.getSession().getAttribute(
						"userInfo");
				log.setUserIp(request.getRemoteAddr());
				log.setUserName(user.getUsername());
				log.setOperateDate(new Date());
				log.setSource(1);
				log.setMessages(baseAction.getLogInfo());
				LogUtil.insertLogInfo(request, log);	
			}
		}
		return ret;
	}

}
