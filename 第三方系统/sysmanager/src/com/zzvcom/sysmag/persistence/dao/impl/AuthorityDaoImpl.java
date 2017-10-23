package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.AuthorityDao;

public class AuthorityDaoImpl extends BaseDaoiBatis implements AuthorityDao {

	public String getAuthSystemId(String roleId, String systemId) throws DataAccessException{
		Map params = new HashMap();
		params.put("roleId", roleId);
		params.put("systemId", systemId);
		return (String)getSqlMapClientTemplate().queryForObject("Auth.getAuthSystemId", params);
	}

	public void deleteDeployNodeAuth(String rsId, String deployNodeId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("rsId", rsId);
		params.put("deployNodeId", deployNodeId);
		getSqlMapClientTemplate().delete("Auth.deleteDeployNodeAuth", params);
	}

	public void deleteModuleAuth(String rnId, String moduleId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("rnId", rnId);
		params.put("moduleId", moduleId);
		getSqlMapClientTemplate().delete("Auth.deleteModuleAuth", params);
	}

	public void deleteOperationAuth(String rmId, String operationId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("rmId", rmId);
		params.put("operationId", operationId);
		getSqlMapClientTemplate().delete("Auth.deleteOperationAuth", params);
	}

	public void deleteSystemAuth(String roleId, String systemId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("roleId", roleId);
		params.put("systemId", systemId);
		getSqlMapClientTemplate().delete("Auth.deleteSystemAuth", params);
	}

	public void insertDeployNodeAuth(String rsId, String deployNodeId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("rsId", rsId);
		params.put("deployNodeId", deployNodeId);
		getSqlMapClientTemplate().insert("Auth.insertDeployNodeAuth", params);
	}

	public void insertModuleAuth(String rnId, String moduleId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("rnId", rnId);
		params.put("moduleId", moduleId);
		getSqlMapClientTemplate().insert("Auth.insertModuleAuth", params);
	}

	public void insertOperationAuth(String rmId, String operationId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("rmId", rmId);
		params.put("operationId", operationId);
		getSqlMapClientTemplate().insert("Auth.insertOperationAuth", params);
	}

	public void insertSystemAuth(String roleId, String systemId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("roleId", roleId);
		params.put("systemId", systemId);
		getSqlMapClientTemplate().insert("Auth.insertSystemAuth", params);
	}

	public String getAuthDeployNodeId(String rsId, String nodeId) throws DataAccessException {
		Map<String,String> params = new HashMap<String,String>();
		params.put("rsId", rsId);
		params.put("nodeId", nodeId);
		return (String)getSqlMapClientTemplate().queryForObject("Auth.getAuthDeployNodeId", params);
	}

	public String getAuthModuleId(String rnId, String moduleId)	throws DataAccessException {
		Map<String,String> params = new HashMap<String,String>();
		params.put("rnId", rnId);
		params.put("moduleId", moduleId);
		return (String)getSqlMapClientTemplate().queryForObject("Auth.getAuthModuleId", params);
	}

	public String getAuthOperationId(String rmId, String operationId) throws DataAccessException {
		Map<String,String> params = new HashMap<String,String>();
		params.put("rmId", rmId);
		params.put("operationId", operationId);
		return (String)getSqlMapClientTemplate().queryForObject("Auth.getAuthOperationId", params);
	}

	public List<String> getAuthDeployNodeIds(String roleId, String systemId)
			throws DataAccessException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("roleId", roleId);
		params.put("systemId", systemId);
		return getSqlMapClientTemplate().queryForList("Auth.getAuthDeployNodeIds", params);
	}

	public List<String> getAuthModuleIds(String roleId, String systemId,
			String deployNodeId) throws DataAccessException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("roleId", roleId);
		params.put("systemId", systemId);
		params.put("deployNodeId", deployNodeId);
		return getSqlMapClientTemplate().queryForList("Auth.getAuthModuleIds", params);
	}

	public List<String> getAuthOperationIds(String roleId, String systemId,
			String deployNodeId, String moduleId) throws DataAccessException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("roleId", roleId);
		params.put("systemId", systemId);
		params.put("deployNodeId", deployNodeId);
		params.put("moduleId", moduleId);
		return getSqlMapClientTemplate().queryForList("Auth.getAuthOperationIds", params);
	}

	public List<String> getAuthSystemIds(String roleId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Auth.getAuthSystemIds", roleId);
	}

	public String getDeployIdByAuthId(String rnId) throws DataAccessException {
		return (String)getSqlMapClientTemplate().queryForObject("Auth.getDeployIdByAuthId", rnId);
	}

	public String getModuleIdByAuthId(String rmId) throws DataAccessException {
		return (String)getSqlMapClientTemplate().queryForObject("Auth.getModuleIdByAuthId", rmId);
	}

	public String getSystemIdByAuthId(String rsId) throws DataAccessException {
		return (String)getSqlMapClientTemplate().queryForObject("Auth.getSystemIdByAuthId", rsId);
	}

	public Map<String, Integer> countAssignedModule(String loginRoleId, String assignedRoleId,
			String systemId, String deployNodeId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("loginRoleId", loginRoleId);
		params.put("assignedRoleId", assignedRoleId);
		params.put("systemId", systemId);
		params.put("deployNodeId", deployNodeId);
		return (Map)getSqlMapClientTemplate().queryForObject("Auth.getAssignedModule", params);
	}

	public Map<String, Integer> countAssignedOperation(String loginRoleId, String assignedRoleId,
			String systemId, String deployNodeId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("loginRoleId", loginRoleId);
		params.put("assignedRoleId", assignedRoleId);
		params.put("systemId", systemId);
		params.put("deployNodeId", deployNodeId);
		return (Map)getSqlMapClientTemplate().queryForObject("Auth.getAssignedOperation", params);
	}

	public Map<String, Integer> countAssignedDeployNode(String loginRoleId, String assignedRoleId,
			String systemId) throws DataAccessException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("loginRoleId", loginRoleId);
		params.put("assignedRoleId", assignedRoleId);
		params.put("systemId", systemId);
		return (Map)getSqlMapClientTemplate().queryForObject("Auth.getAssignedDeployNode", params);
	}


}
