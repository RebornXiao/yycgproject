package zzvcom.sys.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.RoleService;
import zzvcom.sys.util.GetPermission;
import zzvcom.sys.util.ReMsg;
import zzvcom.sys.util.ReMsgUtil;
import zzvcom.util.PageUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author LHQ.
 * 
 */
public class ModuleAction extends BaseAction {

	private static final long serialVersionUID = 200920200001L;
	public static final Logger log = Logger.getLogger(ModuleAction.class);
	private String name;
	private String moduleTree;// 模块（菜单）树
	private int menutype = 1;// 菜单类型
	private int depth;// 菜单深度
	private String parentid;//  
	private List<VcomSysOperation> operList;// 操作列表
	private VcomSysOperation operObj = new VcomSysOperation();// 操作对象
	private String id;//  
	private String moduleid;// 模块id
	private RoleService roleService;

	/**
	 * 根据父类id获得模块列表
	 * 
	 * @return
	 */
	public String getModuleListByParentid() {

		String modulename = name;

		PageUtil page = new PageUtil();
		page.setCurPage(this.getPageIndex());

		this.moduleList = moduleService.getModuleListByParentid(parentid,
				modulename, page);
		log.info(moduleList.size());
		this.setPageBar(page.getToolsMenu());
		return SUCCESS;
	}

	/**
	 * 根据moduleid获得模块列表
	 * 
	 * @return
	 */
	public String getOperationListByModuleid() {

		// 判断是否包含子模块
		this.moduleList = moduleService.getModuleListByParentid(parentid, null,
				null);
		if (null == moduleList || moduleList.isEmpty()) {
			// 显示操作列表

			String opername = name;

			PageUtil page = new PageUtil();
			page.setCurPage(this.getPageIndex());
			String moduleid = parentid;
			this.operList = moduleService.getOperationListByModuleid(moduleid,
					opername, page);

			this.setPageBar(page.getToolsMenu());
			return SUCCESS;
		}

		else
			return "tip";
	}

	/**
	 * 保存模块
	 * 
	 * @return
	 * @throws SaveOrUpdateException
	 */
	public String saveModule() throws SaveOrUpdateException {

		ReMsgUtil.putMsg(moduleService.saveModule(moduleObj), request, "模块");
		return INPUT;
	}

	/**
	 * 修改模块
	 * 
	 * @return
	 * @throws SaveOrUpdateException
	 */
	public String updateModule() throws SaveOrUpdateException {
		ReMsgUtil.putMsg(moduleService.updateModule(moduleObj), request, "模块");
		return INPUT;
	}

	/**
	 * 跳转至模块新增或者修改页面
	 * 
	 * @return
	 * @throws ObjectNotFindException
	 */
	public String toSaveOrUpdateModulePage() throws ObjectNotFindException {
		if (1 == operFlag) {
			// 取出即将修改的数据
			this.moduleObj = moduleService.getModuleObjById(id);
		}
		return SUCCESS;
	}

	/**
	 * 跳转至操作新增或者修改页面
	 * 
	 * @return
	 * @throws ObjectNotFindException
	 */
	public String toSaveOrUpdateOperPage() throws ObjectNotFindException {
		if (1 == operFlag) {
			// 取出即将修改的数据
			this.operObj = moduleService.getOperById(id);
		}
		return SUCCESS;
	}

	/**
	 * 保存操作
	 * 
	 * @return
	 * @throws SaveOrUpdateException
	 * @throws Exception
	 */
	public String saveOper() throws SaveOrUpdateException {

		ReMsgUtil.putMsg(moduleService.saveOper(operObj), request, "操作");
		return INPUT;
	}

	/**
	 * 修改操作
	 * 
	 * @return
	 * @throws SaveOrUpdateException
	 * @throws Exception
	 */
	public String updateOper() throws SaveOrUpdateException {
		ReMsgUtil.putMsg(moduleService.updateOper(operObj), request, "操作");

		//

		return INPUT;
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteModule() throws ObjectNotFindException, DeleteException {

		String[] checkboxIds = ServletActionContext.getRequest()
				.getParameterValues("checkboxId");

		if (null != checkboxIds && 0 != checkboxIds.length) {
			ReMsg[] rms = new ReMsg[checkboxIds.length];
			int i = 0;
			for (String checkboxId : checkboxIds) {
				rms[i] = moduleService.deleteModule(checkboxId);
				i++;
			}// end of for
			ReMsgUtil.putMsg(rms, request, "模块");
		}
		return getModuleListByParentid();
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteOper() throws ObjectNotFindException, DeleteException {

		//
		String[] checkboxIds = ServletActionContext.getRequest()
				.getParameterValues("checkboxId");

		if (null != checkboxIds && 0 != checkboxIds.length) {
			ReMsg[] rms = new ReMsg[checkboxIds.length];
			int i = 0;
			for (String checkboxId : checkboxIds) {
				rms[i] = moduleService.deleteOper(checkboxId);
				i++;
			}// end of for
			ReMsgUtil.putMsg(rms, request, "操作");
		}
		getOperationListByModuleid();
		return SUCCESS;
	}

	/**
	 * 获得模块树
	 * 
	 * @return
	 */
	public String getModuleTreeUI() {
		VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession()
				.get("userInfo");
		if (null == usr)
			return SUCCESS;
		if ("admin".equals(usr.getUsercode()))
			moduleList = moduleService.getModuleList();
		else {
			
			List<VcomSysRole> rolelist=roleService.getRoleByIds(usr.getRole());
			String ids1="";
			//per=user.getPermissions();
			for (VcomSysRole us : rolelist) {//组合角色权限
				ids1=ids1+us.getPermissions()+",";
			}
			
			
			
			String ids = GetPermission.getPer(ids1, true, true);
			moduleList = moduleService.getModuleListByIds(ids, true);
		}

		String retTree = "var rootTree = new WebFXTree('所有模块','javascript:openModuleIframe(-1,1)','other');";
		for (int i = 0; i < moduleList.size(); i++) {
			moduleObj = moduleList.get(i);
			// 展开站点，并以列表形式显示栏目列表

			int depth = moduleObj.getDepth() + 1;
			String moduleid = moduleObj.getId().replace(".", "X");
			String parentid = moduleObj.getParentid().replace(".", "X");
			// 声明节点对象
			retTree += "\n var m_" + moduleid
					+ " = new WebFXTreeItem(\"" + moduleObj.getModulename()
					+ "\", \"javascript:" + "openModuleIframe('"
					+ moduleObj.getId() + "'," + depth + ")\",null,'m_"
					+ moduleid + "');";

			if (1 == moduleObj.getDepth() || "-1" == moduleObj.getParentid())// 如果深度为1（）则直接加入根结点中
				retTree += "\n rootTree.add(m_" + moduleid + ");";
			else
				retTree += "\n m_" + parentid + ".add(m_"
						+ moduleid + ");";

			// 显示操作菜单
			// if (2 == this.menutype) {
			// long moduleid = moduleObj.getId();
			// operList = moduleService.getOperationListByModuleid(moduleid,
			// null, null);
			// for (VcomSysOperation operObj : operList) {
			// // 声明节点对象
			// retTree += "\n var o_" + operObj.getId()
			// + " = new WebFXTreeItem(\"" + operObj.getOpername()
			// + "\", \"javascript:" + "openModuleIframe("
			// + moduleObj.getId() + "," + moduleObj.getDepth()
			// + 1 + ")\",null,'o_" + operObj.getId() + "');";
			// retTree += "\n m_" + moduleObj.getId() + ".add(o_"
			// + operObj.getId() + ");";
			// }// end of for
			//
			// }
		}

		retTree += "document.write(rootTree);\n" + "rootTree.expand();";
		moduleTree = retTree;
		return SUCCESS;
	}

	public String getModuleTree() {
		return moduleTree;
	}

	public void setModuleTree(String moduleTree) {
		this.moduleTree = moduleTree;
	}

	public int getMenutype() {
		return menutype;
	}

	public void setMenutype(int menutype) {
		this.menutype = menutype;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public List<VcomSysOperation> getOperList() {
		return operList;
	}

	public void setOperList(List<VcomSysOperation> operList) {
		this.operList = operList;
	}

	public VcomSysOperation getOperObj() {
		return operObj;
	}

	public void setOperObj(VcomSysOperation operObj) {
		this.operObj = operObj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}
