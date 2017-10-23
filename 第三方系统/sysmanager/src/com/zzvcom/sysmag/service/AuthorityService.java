package com.zzvcom.sysmag.service;

import java.util.List;

import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.TreeNode;
import com.zzvcom.sysmag.pojo.User;

/**
 * 权限分配Service接口
 * @author Wang Xiaoming
 */
public interface AuthorityService {
	
	public void saveAuth(String roleId, String systemId, String deployNodeId, List<String> nodeList);
	
	/**
	 * 根据操作编码加载操作工具条
	 * @param operationIds 操作编码(格式: 操作编码,操作编码,...,操作编码,)
	 * @return 操作工具条Json
	 */
	public String loadOperationToolBar(String operationIds);
	
	/**
	 * 加载特定角色的子系统列表<br>
	 * 包含子系统分配权限的状态(全部分配完毕, 部分分配, 没有进行任何分配)
	 * @param role 角色
	 * @return 子系统列表
	 */
	
	public List<TreeNode> loadSystemTree(User loginUser, String roleId);
	
	public List<TreeNode> loadModuleTree(User loginUser, String roleId, String systemId, String deployNodeId);
	
	public void saveBatchAuth(User loginUser, String roleId, String systemId, String deployNodeId);
}
