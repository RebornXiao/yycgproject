package com.zzvcom.sysmag.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.persistence.dao.AuthorityDao;
import com.zzvcom.sysmag.persistence.dao.DeployNodeDao;
import com.zzvcom.sysmag.persistence.dao.ModuleDao;
import com.zzvcom.sysmag.persistence.dao.OperationDao;
import com.zzvcom.sysmag.persistence.dao.RoleDao;
import com.zzvcom.sysmag.persistence.dao.SubSystemDao;
import com.zzvcom.sysmag.pojo.AuthNode;
import com.zzvcom.sysmag.pojo.DeployNode;
import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.SystemAssignStatus;
import com.zzvcom.sysmag.pojo.TreeNode;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AuthorityService;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDao authDao;
	@Autowired
	private OperationDao optDao;
	@Autowired
	private SubSystemDao systemDao;
	@Autowired
	private DeployNodeDao deployNodeDao;
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private RoleDao roleDao;

	@Transactional
	public void saveAuth(String roleId, String systemId, String deployNodeId, List<String> nodeList) {
		deleteAuth(roleId, systemId, deployNodeId, nodeList);

		String rsId = "";
		String rnId = "";
		String rmId = "";

		if (!nodeList.isEmpty()) {
			rsId = authDao.getAuthSystemId(roleId, systemId);
			if (null == rsId) {
				authDao.insertSystemAuth(roleId, systemId);
				rsId = authDao.getAuthSystemId(roleId, systemId);
			}
			rnId = authDao.getAuthDeployNodeId(rsId, deployNodeId);
			if (null == rnId) {
				authDao.insertDeployNodeAuth(rsId, deployNodeId);
				rnId = authDao.getAuthDeployNodeId(rsId, deployNodeId);
			}

			for (String nodeIds : nodeList) {
				String[] ids = nodeIds.split("/");
				List<AuthNode> authNodeList = new ArrayList<AuthNode>();
				for (int i = 2; i < ids.length; i++) {
					authNodeList.add(new AuthNode(ids[i]));
				}

				for (AuthNode authNode : authNodeList) {
					if (authNode.isTopModule() || authNode.isBottomModule()) {
						rmId = authDao.getAuthModuleId(rnId, authNode.getId());
						if (null == rmId) {
							authDao.insertModuleAuth(rnId, authNode.getId());
							rmId = authDao.getAuthModuleId(rnId, authNode.getId());
						}
						if(authNode.isBottomModule()) {
							authDao.deleteOperationAuth(rmId, null);
							String bottomModuleId = authDao.getModuleIdByAuthId(rmId);
							List<Operation> operateList = optDao.getOperationList(bottomModuleId);
							if(null != operateList && operateList.size() > 0) {
								for(Operation operobj : operateList) {
									authDao.insertOperationAuth(rmId, operobj.getOperationId());
								}
							}
						}
					} else if (authNode.isOperation()) {
						authDao.insertOperationAuth(rmId, authNode.getId());
					}
				}
			}

		}
	}

	private void deleteAuth(String roleId, String systemId, String deployNodeId, List<String> nodeList) {
		String rsId = authDao.getAuthSystemId(roleId, systemId);
		if (rsId != null) {
			if (deployNodeId == null || deployNodeId.equals("")) {
				List<String> dnIdList = authDao.getAuthDeployNodeIds(roleId, systemId);
				if (!dnIdList.isEmpty()) {
					for (String dnId : dnIdList) {
						removeModuleAuth(roleId, systemId, dnId, rsId);
					}
				}
			} else {
				removeModuleAuth(roleId, systemId, deployNodeId, rsId);
			}
			
		}

		if (nodeList.isEmpty()) {
			authDao.deleteDeployNodeAuth(rsId, deployNodeId);
		}
		List<String> dnList = authDao.getAuthDeployNodeIds(roleId, systemId);

		if (dnList.isEmpty()) {
			authDao.deleteSystemAuth(roleId, systemId);
		}
	}

	private void removeModuleAuth(String roleId, String systemId, String deployNodeId,
			String rsId) {
		String rnId = authDao.getAuthDeployNodeId(rsId, deployNodeId);
		if (rnId != null) {
			List<String> moduleIdList = authDao.getAuthModuleIds(roleId, systemId, deployNodeId);
			if (!moduleIdList.isEmpty()) {
				for (String moduleId : moduleIdList) {
					authDao.deleteOperationAuth(authDao.getAuthModuleId(rnId, moduleId), null);
				}
				authDao.deleteModuleAuth(rnId, null);
			}
		}
	}

	@Transactional(readOnly = true)
	public String loadOperationToolBar(String operationIds) {
		List<Operation> optList = optDao.getOperationListByIds(Arrays.asList(operationIds.split(",")));

		if (optList.isEmpty()) {
			return "";
		}

		StringBuilder tbJson = new StringBuilder();

		for (Operation operation : optList) {
			String item = "{id:'" + operation.getOperationId() + "',text:'" + operation.getOperationName() + "',icon:'" + operation.getIcon() + "',cls:'x-btn-text-icon bmenu',handler:"
					+ operation.getMethod() + "},";
			tbJson.append(item).append("'-',");
		}

		if (tbJson.length() > 0) {
			return "[" + tbJson.substring(0, tbJson.length() - 1) + "]";
		} else {
			return "[" + tbJson + "]";
		}

	}

	@Transactional(readOnly = true)
	public List<TreeNode> loadModuleTree(User loginUser, String roleId, String systemId, String deployNodeId) {
		List<TreeNode> moduleTree = new ArrayList<TreeNode>();
		Role userRole = null;
		List<Module> moduleList = null;
		if("admin".equals(loginUser.getUserName())) {
			userRole = new Role("0");
			moduleList = loadAdminModuleList(systemId, Module.ROOT_MODULE_ID);
		} else {
			userRole = roleDao.getUserRole(loginUser.getUserName());
			moduleList = loadModuleList(userRole, systemId, deployNodeId, Module.ROOT_MODULE_ID);
		}
		List<String> authModuleIds = authDao.getAuthModuleIds(roleId, systemId, deployNodeId);
		for (Module module : moduleList) {
			TreeNode mdlNode = new TreeNode();
			mdlNode.setId("t" + module.getModuleId());
			mdlNode.setText(module.getModuleName());
			//mdlNode.setIcon(module.getIcon());
			mdlNode.setIconCls("node-module");
			if (authModuleIds.contains(module.getModuleId())) {
				mdlNode.setChecked(true);
			}

			mdlNode.setChildren(loadBottomModuleTree(loginUser, roleId, systemId, deployNodeId, module.getModuleId()));

			moduleTree.add(mdlNode);
		}

		return moduleTree;
	}
	
	
	/*private List<TreeNode> loadMiddleModuleTree(User loginUser, String roleId, String systemId, String deployNodeId, String moduleId) {
		List<TreeNode> moduleTree = new ArrayList<TreeNode>();
		Role userRole = null;
		List<Module> moduleList = null;
		if("admin".equals(loginUser.getUserName())) {
			userRole = new Role("0");
			moduleList = loadAdminModuleList(systemId, moduleId);
		} else {
			userRole = roleDao.getUserRole(loginUser.getUserName());
			moduleList = loadModuleList(userRole, systemId, deployNodeId, moduleId);
		}
		List<String> authModuleIds = authDao.getAuthModuleIds(roleId, systemId, deployNodeId);
		for (Module module : moduleList) {
			TreeNode mdlNode = new TreeNode();
			mdlNode.setId("m" + module.getModuleId());
			mdlNode.setText(module.getModuleName());
			//mdlNode.setIcon(module.getIcon());
			mdlNode.setIconCls("node-module");
			// 设置权限分配状态
			if (authModuleIds.contains(module.getModuleId())) {
				mdlNode.setChecked(true);
			}

			mdlNode.setChildren(loadBottomModuleTree(loginUser, roleId, systemId, deployNodeId, module.getModuleId()));

			moduleTree.add(mdlNode);
		}

		return moduleTree;
	}*/

	private List<TreeNode> loadBottomModuleTree(User loginUser, String roleId, String systemId, String deployNodeId, String moduleId) {
		List<TreeNode> moduleTree = new ArrayList<TreeNode>();
		Role userRole = null;
		List<Module> moduleList = null;
		if("admin".equals(loginUser.getUserName())) {
			userRole = new Role("0");
			moduleList = loadAdminModuleList(systemId, moduleId);
		} else {
			userRole = roleDao.getUserRole(loginUser.getUserName());
			moduleList = loadModuleList(userRole, systemId, deployNodeId, moduleId);
		}
		List<String> authModuleIds = authDao.getAuthModuleIds(roleId, systemId, deployNodeId);
		for (Module module : moduleList) {
			TreeNode mdlNode = new TreeNode();
			mdlNode.setId("b" + module.getModuleId());
			mdlNode.setText(module.getModuleName());
			//mdlNode.setIcon(module.getIcon());
			mdlNode.setIconCls("node-module-bottom");
			// 设置权限分配状态
			if (authModuleIds.contains(module.getModuleId())) {
				mdlNode.setChecked(true);
			}

//			mdlNode.setChildren(loadOperationTree(loginUser, roleId, systemId, deployNodeId, module.getModuleId()));
			mdlNode.setLeaf(true);
			moduleTree.add(mdlNode);
		}

		return moduleTree;
	}

	/*private List<TreeNode> loadOperationTree(User loginUser, String roleId, String systemId, String deployNodeId, String moduleId) {
		List<TreeNode> optTree = new ArrayList<TreeNode>();
		List<Operation> optList = loadOperationList(loginUser.getRole(), systemId, deployNodeId, moduleId);
		List<String> authModuleIds = authDao.getAuthOperationIds(roleId, systemId, deployNodeId, moduleId);
		for (Operation opt : optList) {
			TreeNode optNode = new TreeNode();
			optNode.setId("o" + opt.getOperationId());
			optNode.setText(opt.getOperationName());
			//optNode.setIcon(opt.getIcon());
			optNode.setIconCls("node-operation");
			
			if (authModuleIds.contains(opt.getOperationId())) {
				optNode.setChecked(true);
			}
			optNode.setLeaf(true);
			optTree.add(optNode);
		}
		return optTree;
	}*/

	/**
	 * 根据权限过滤子系统列表
	 * 
	 * @param role
	 *            角色
	 * @return 该角色管辖的子系统列表
	 */
	private List<SubSystem> loadSystemList(Role role) {
		List<SubSystem> systemList = systemDao.getSystemList();
		if (role.isRootRole()) {
			return systemList;
		}

		List<SubSystem> authSystemList = new ArrayList<SubSystem>();

		List<String> authSystemIds = authDao.getAuthSystemIds(role.getRoleId());

		for (SubSystem system : systemList) {
			if (authSystemIds.contains(system.getSystemId())) {
				authSystemList.add(system);
			}
		}

		return authSystemList;
	}

	/**
	 * 根据权限过滤部署节点列表
	 * 
	 * @param role
	 *            角色
	 * @param systemId
	 *            子系统Id
	 * @return 该角色管线的特定子系统下的部署节点列表
	 */
	private List<DeployNode> loadDeployNodeList(Role role, String systemId) {
		List<DeployNode> deployNodeList = deployNodeDao.getDeployNodeList(systemId);
		if (role.isRootRole()) {
			return deployNodeList;
		}

		List<DeployNode> authDnList = new ArrayList<DeployNode>();

		List<String> authDeployNodeIds = authDao.getAuthDeployNodeIds(role.getRoleId(), systemId);

		for (DeployNode deployNode : deployNodeList) {
			if (authDeployNodeIds.contains(deployNode.getNodeId())) {
				authDnList.add(deployNode);
			}
		}

		return authDnList;
	}

	private List<Module> loadAdminModuleList(String systemId, String upperModuleId) {
		List<Module> moduleList = moduleDao.getModuleList(systemId, upperModuleId);
		return moduleList;
	}
	
	private List<Module> loadModuleList(Role role, String systemId, String deployNodeId, String upperModuleId) {
		List<Module> moduleList = moduleDao.getModuleList(role, systemId, deployNodeId, upperModuleId);
		return moduleList;
	}
	
	private List<Operation> loadOperationList(Role role, String systemId, String deployNodeId, String moduleId) {
		List<Operation> optList = optDao.getOperationList(moduleId);
		if (role.isRootRole()) {
			return optList;
		}

		List<Operation> authOptList = new ArrayList<Operation>();
		List<String> authOptIds = authDao.getAuthOperationIds(role.getRoleId(), systemId, deployNodeId, moduleId);
		for (Operation operation : optList) {
			if (authOptIds.contains(operation.getOperationId())) {
				authOptList.add(operation);
			}
		}
		return authOptList;
	}

	@Transactional(readOnly = true)
	public List<TreeNode> loadSystemTree(User loginUser, String roleId) {
		List<SubSystem> systemList = loadSystemList(loginUser.getRole());
		List<TreeNode> systemTree = new ArrayList<TreeNode>();
		for (SubSystem system : systemList) {
			TreeNode sysNode = new TreeNode();
			sysNode.setId("s" + system.getSystemId());
			sysNode.setText(system.getSystemName());
			//sysNode.setIcon("image/icons/monitor.png");
			sysNode.setIconCls("node-system-normal");
			sysNode.setCls("x-tree-node-sys");
			sysNode.setQtip("点击右键可快速分配权限");
			List<DeployNode> deployNodeList = loadDeployNodeList(loginUser.getRole(), system.getSystemId());
			List<TreeNode> dnTree = new ArrayList<TreeNode>();

			int sysStatus = 0;

			for (DeployNode deployNode : deployNodeList) {
				TreeNode dnNode = new TreeNode();
				dnNode.setId("n" + deployNode.getNodeId());
				dnNode.setText(deployNode.getNodeName());
				dnNode.setCls("x-tree-node-sys");
				dnNode.setQtip("点击右键可快速分配权限");
				dnNode.setLeaf(true);

				SystemAssignStatus status = addAssignAuthStatus(loginUser, roleId, system.getSystemId(), deployNode.getNodeId());
				if (status.equals(SystemAssignStatus.ALL)) {
					dnNode.setIconCls("node-assign-all-cls");
				} else if (status.equals(SystemAssignStatus.SOME)) {
					dnNode.setIconCls("node-assign-some-cls");
					sysStatus++;
				} else {
					dnNode.setIconCls("node-assign-none-cls");
					sysStatus++;
				}

				dnTree.add(dnNode);

			}
			// 为子系统树节点添加子节点
			sysNode.setChildren(dnTree);
			if (sysStatus > 0) {
				sysNode.setIconCls("node-system-error");
			}
			systemTree.add(sysNode);
		}
		return systemTree;
	}

	private SystemAssignStatus addAssignAuthStatus(User loginUser, String roleId, String systemId, String deployNodeId) {
		Map<String,Integer> moduleAssign = authDao.countAssignedModule(loginUser.getRole().getRoleId(), roleId, systemId, deployNodeId);
		int fullCount = moduleAssign.get("full");
		int assigned = moduleAssign.get("assign");
		if (assigned == 0) {
			return SystemAssignStatus.NONE;
		}
		if (assigned < fullCount) {
			return SystemAssignStatus.SOME;
		}
		if (assigned == fullCount) {
			Map<String,Integer> optAssign = authDao.countAssignedOperation(loginUser.getRole().getRoleId(), roleId, systemId, deployNodeId);
			fullCount = optAssign.get("full");
			assigned = optAssign.get("assign");
			if (assigned < fullCount) {
				return SystemAssignStatus.SOME;
			}
		}
		
		return SystemAssignStatus.ALL;
		
		/*List<String> authList = new ArrayList<String>();
		authList = authDao.getAuthModuleIds(roleId, systemId, deployNodeId);
		if (authList.isEmpty()) {
			return SystemAssignStatus.NONE;
		}
		List<Module> moduleList = this.loadModuleList(loginUser.getRole(), systemId, deployNodeId, null);
		if (authList.size() < moduleList.size()) {
			return SystemAssignStatus.SOME;
		}
		if (authList.size() == moduleList.size()) {
			for (Module module : moduleList) {
				if (!module.isTopModule()) {
					authList = authDao.getAuthOperationIds(roleId, systemId, deployNodeId, module.getModuleId());
					if (authList.isEmpty()) {
						return SystemAssignStatus.SOME;
					}
					List<Operation> optList = this.loadOperationList(loginUser.getRole(), systemId, deployNodeId, module.getModuleId());
					if (authList.size() < optList.size()) {
						return SystemAssignStatus.SOME;
					}
				}

			}
		}
		return SystemAssignStatus.ALL;*/
	}

	@Transactional
	public void saveBatchAuth(User loginUser, String roleId, String systemId, String deployNodeId) {
		deleteAuth(roleId, systemId, deployNodeId, new ArrayList<String>());

		String rsId = "";
		String rnId = "";
		//String rmId = "";

		rsId = authDao.getAuthSystemId(roleId, systemId);
		if (null == rsId) {
			authDao.insertSystemAuth(roleId, systemId);
			rsId = authDao.getAuthSystemId(roleId, systemId);
		}
		
		if (deployNodeId == null || deployNodeId.equals("")) {
			List<DeployNode> dnList = this.loadDeployNodeList(loginUser.getRole(), systemId);
			if (!dnList.isEmpty()) {
				for (DeployNode dn : dnList) {
					saveDeployNodeAuth(loginUser, systemId, dn.getNodeId(), rsId);
				}
			}
		} else {
			saveDeployNodeAuth(loginUser, systemId, deployNodeId, rsId);
		}
	}

	private void saveDeployNodeAuth(User loginUser, String systemId, String deployNodeId,
			String rsId) {
		String rnId;
		String rmId;
		rnId = authDao.getAuthDeployNodeId(rsId, deployNodeId);
		if (null == rnId) {
			authDao.insertDeployNodeAuth(rsId, deployNodeId);
			rnId = authDao.getAuthDeployNodeId(rsId, deployNodeId);
		}

		List<Module> moduleList = loadModuleList(loginUser.getRole(), systemId, deployNodeId, null);

		if (!moduleList.isEmpty()) {
			for (Module module : moduleList) {
				authDao.insertModuleAuth(rnId, module.getModuleId());
				rmId = authDao.getAuthModuleId(rnId, module.getModuleId());
				List<Operation> optList = loadOperationList(loginUser.getRole(), systemId, deployNodeId, module.getModuleId());
				if (!optList.isEmpty()) {
					for (Operation operation : optList) {
						authDao.insertOperationAuth(rmId, operation.getOperationId());
					}
				}
			}
		}
	}

}
