package com.zzvcom.sysmag.persistence.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.RoleDao;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;

public class RoleDaoImpl extends BaseDaoiBatis implements RoleDao {

	public void deleteRole(String roleId) throws DataAccessException {
		getSqlMapClientTemplate().delete("Role.deleteRole", roleId);
	}

	public Role getRoleById(String roleId) throws DataAccessException {
		return (Role)getSqlMapClientTemplate().queryForObject("Role.getRoleById", roleId);
	}

	public Role getRoleByName(String roleName) throws DataAccessException {
		return (Role)getSqlMapClientTemplate().queryForObject("Role.getRoleByName", roleName);
	}

	public List<Role> getRoleList() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Role.getAllRoleList");
	}
	
	public List<Role> getAddOrEditRoleList(String userName, String cmd, String edituser) throws DataAccessException {
		Map params = new HashMap();	
		params.put("userName", userName);
		params.put("cmd", cmd);
		params.put("editUser", edituser);
		return getSqlMapClientTemplate().queryForList("Role.getAddOrEditRoleList", params);
	}

	public List<Role> getUserControlRoleList(String userName)
			throws DataAccessException {
		Map params = new HashMap();	
		params.put("userName", userName);
		return getSqlMapClientTemplate().queryForList("Role.getUserControlRoleList", params);
	}

	public Role getUserRole(String userName) throws DataAccessException {
		return (Role)getSqlMapClientTemplate().queryForObject("Role.getUserRole", userName);
	}

	public void insertRole(Role role) throws DataAccessException {
		getSqlMapClientTemplate().insert("Role.insertRole", role);

	}

	public void updateRole(Role role) throws DataAccessException {
		getSqlMapClientTemplate().update("Role.updateRole", role);

	}

	public PagingResultDTO getRoleList(int start, int limit)
			throws DataAccessException {
		return getPagingResultMap("Role.getAllRoleList", null, start, limit);
	}

	public PagingResultDTO getRoleList(String userName, int start, int limit)
			throws DataAccessException {
		Map params = new HashMap();	
		params.put("userName", userName);
		return getPagingResultMap("Role.getUserControlRoleList", params, start, limit);
	}

	public List<Role> getUserUsingRoleList(String roleId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Role.getUserUsingRoleList", roleId);
	}
}
