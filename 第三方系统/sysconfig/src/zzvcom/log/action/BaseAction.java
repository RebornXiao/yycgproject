package zzvcom.log.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import zzvcom.log.pojo.VcomSysLog;
import zzvcom.log.service.VcomSysLogService;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.ModuleService;
import zzvcom.sys.service.RoleService;
import zzvcom.sys.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private static final long serialVersionUID = 6126905657342889076L;
	private String pageBar;// 分页条
	private int pageIndex = 1;// 页码
	public String logInfo;//日志信息
	public boolean isLog=false;//是否添加日志信息
	private VcomSysLogService vcomSysLogService;
	private List<VcomSysLog> logList = new ArrayList<VcomSysLog>();

	private VcomSysLog vcomSysLog;

	public List<VcomSysLog> getLogList() {
		return logList;
	}

	public void setLogList(List<VcomSysLog> logList) {
		this.logList = logList;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getPageBar() {
		return pageBar;
	}

	public void setPageBar(String pageBar) {
		this.pageBar = pageBar;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}

	public VcomSysLogService getVcomSysLogService() {
		return vcomSysLogService;
	}

	public void setVcomSysLogService(VcomSysLogService vcomSysLogService) {
		this.vcomSysLogService = vcomSysLogService;
	}

	public VcomSysLog getVcomSysLog() {
		return vcomSysLog;
	}

	public void setVcomSysLog(VcomSysLog vcomSysLog) {
		this.vcomSysLog = vcomSysLog;
	}

}
