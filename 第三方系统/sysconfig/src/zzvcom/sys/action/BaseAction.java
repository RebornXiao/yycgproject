package zzvcom.sys.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.ModuleService;
import zzvcom.sys.service.RoleService;
import zzvcom.sys.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private static final long serialVersionUID = 6126905657342889076L;
	private String pageBar;// 分页条
	private int pageIndex = 1;// 页码
	private String rightTree;
	private String leftTree;
	protected List<VcomSysModule> moduleList;// 模块（菜单）列表
	private List<VcomSysUser> userList;//用户列表
	protected VcomSysModule moduleObj = new VcomSysModule();// 模块（菜单）对象
	protected ModuleService moduleService;
	private UserService userService;
	protected int operFlag = 0;// 操作标帜 0 -新增 1-修改

	// 角色部分
	private List<VcomSysRole> roleList;// 角色列表
	private VcomSysRole roleobj = new VcomSysRole();// 角色对象
	private RoleService roleService;
	private String backvalue = "success";
	public String logInfo;//日志信息
	public boolean isLog=false;//是否添加日志信息
	
	public String resulturl;//提示信息
	public String nexturl;//返回信息
	VcomSysUser user = new VcomSysUser();

	public List<VcomSysRole> getRoleList() {
		return roleList;
	}

	public String getRightTree() {
		return rightTree;
	}

	public void setRightTree(String rightTree) {
		this.rightTree = rightTree;
	}

	public String getBackvalue() {
		return backvalue;
	}

	public void setBackvalue(String backvalue) {
		this.backvalue = backvalue;
	}

	public String getLeftTree() {
		return leftTree;
	}

	public void setLeftTree(String leftTree) {
		this.leftTree = leftTree;
	}

	public void setRoleList(List<VcomSysRole> roleList) {
		this.roleList = roleList;
	}

	public VcomSysRole getRoleobj() {
		return roleobj;
	}

	public void setRoleobj(VcomSysRole roleobj) {
		this.roleobj = roleobj;
	}

	public List<VcomSysModule> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<VcomSysModule> moduleList) {
		this.moduleList = moduleList;
	}

	public VcomSysModule getModuleObj() {
		return moduleObj;
	}

	public void setModuleObj(VcomSysModule moduleObj) {
		this.moduleObj = moduleObj;
	}

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public int getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(int operFlag) {
		this.operFlag = operFlag;
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

	public VcomSysUser getUser() {
		return user;
	}

	public void setUser(VcomSysUser user) {
		this.user = user;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<VcomSysUser> getUserList() {
		return userList;
	}

	public void setUserList(List<VcomSysUser> userList) {
		this.userList = userList;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

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

	public String getResulturl() {
		return resulturl;
	}

	public void setResulturl(String resulturl) {
		this.resulturl = resulturl;
	}

	public String getNexturl() {
		return nexturl;
	}

	public void setNexturl(String nexturl) {
		this.nexturl = nexturl;
	}

}
