package com.zzvcom.sysmag.service;

import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.User;

/**
 * 角色Service接口
 * @author Wang Xiaoming
 */
public interface RoleService {
	public Role getRole(String roleId);
	public void saveRole(Role role);
	public void removeRole(String roleId);
	public Role getRoleById(String roleId);
	public PagingResultDTO loadRoleList(User user, int start, int limit);
	
}
