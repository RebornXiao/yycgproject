package com.zzvcom.sysmag.persistence.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AuthorityDao {
	public List<String> getAuthSystemIds(String roleId) throws DataAccessException;

	public List<String> getAuthDeployNodeIds(String roleId, String systemId)
			throws DataAccessException;

	public List<String> getAuthModuleIds(String roleId, String systemId, String deployNodeId)
			throws DataAccessException;

	public List<String> getAuthOperationIds(String roleId, String systemId, String deployNodeId,
			String moduleId) throws DataAccessException;

	public String getSystemIdByAuthId(String rsId) throws DataAccessException;

	public String getDeployIdByAuthId(String rnId) throws DataAccessException;

	public String getModuleIdByAuthId(String rmId) throws DataAccessException;

	public String getAuthSystemId(String roleId, String systemId) throws DataAccessException;

	public String getAuthDeployNodeId(String rsId, String nodeId) throws DataAccessException;

	public String getAuthModuleId(String rnId, String moduleId) throws DataAccessException;

	public String getAuthOperationId(String rmId, String operationId) throws DataAccessException;

	public Map<String, Integer> countAssignedDeployNode(String loginRoleId, String assignedRoleId,
			String systemId) throws DataAccessException;

	public Map<String, Integer> countAssignedModule(String loginRoleId, String assignedRoleId,
			String systemId, String deployNodeId) throws DataAccessException;

	public Map<String, Integer> countAssignedOperation(String loginRoleId, String assigendRoleId,
			String systemId, String deployNodeId) throws DataAccessException;

	public void insertSystemAuth(String roleId, String systemId) throws DataAccessException;

	public void insertDeployNodeAuth(String rsId, String deployNodeId) throws DataAccessException;

	public void insertModuleAuth(String rnId, String moduleId) throws DataAccessException;

	public void insertOperationAuth(String rmId, String operationId) throws DataAccessException;

	public void deleteSystemAuth(String roleId, String systemId) throws DataAccessException;

	public void deleteDeployNodeAuth(String rsId, String deployNodeId) throws DataAccessException;

	public void deleteModuleAuth(String rnId, String moduleId) throws DataAccessException;

	public void deleteOperationAuth(String rmId, String operationId) throws DataAccessException;

}
