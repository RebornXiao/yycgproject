package com.zzvcom.sysmag.service;

import java.util.List;
import java.util.Map;

import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.User;

/**
 * 系统模块管理Serivce接口
 * @author Wang Xiaoming
 */
public interface ModuleService {
	/**
	 * 根据角色类型加载子系统列表
	 * @param role 角色
	 * @return 子系统列表
	 */
	public List<SubSystem> loadSystemList(Role role);
	
	/**
	 * 加载模块列表
	 * @param role 角色
	 * @param systemId 所属子系统
	 * @param upperModuleId 上级模块Id
	 * @return 模块列表
	 */
	public List<Module> loadModuleList(Role role, String systemId, String upperModuleId);
	
	/**
	 * 加载操作列表
	 * @param role 角色
	 * @param systemId 所属子系统
	 * @param bottomModuleId 模块Id
	 * @return 操作列表
	 */
	public List<Operation> loadOperationList(Role role, String systemId, String bottomModuleId);
	
	public void saveModule(Module module, String cmd);
	public void saveOperation(User loginUser,Operation operation, String cmd);
	
	public void removeModule(String moduleId);
	public void removeOperation(User loginUser, String operationId);
	
	public void saveOrder(String itemIds, String type);
	
	/**
	 * 加载菜单
	 * 子系统、模块、二级模块、操作的层次菜单(通常为树)
	 * @param role 角色
	 * @param menuId 层次菜单项Id
	 * @return Map 包含层次菜单数据列表，及数据类型
	 */
	public Map load(Role role, String menuId);
	public Module getModuleById(String moduleId);
	public Operation getOperationById(String operationId);
}
