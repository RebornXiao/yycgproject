package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.SubSystemDao;
import com.zzvcom.sysmag.pojo.SubSystem;

public class SubSystemDaoImpl extends BaseDaoiBatis implements SubSystemDao {

	public SubSystem getSystemById(String systemId) throws DataAccessException {
		return (SubSystem)getSqlMapClientTemplate().queryForObject("System.getSystemById", systemId);
	}

	public SubSystem getSystemByName(String systemName) throws DataAccessException {
		return (SubSystem)getSqlMapClientTemplate().queryForObject("System.getSystemByName", systemName);
	}

	public List<SubSystem> getSystemList() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("System.getAllSystemList");
	}

	public List<SubSystem> getSystemList(String roleId) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("System.getRoleSystemList", roleId);
	}

	public SubSystem getSystemByDeployNodeId(String deployNodeId)
			throws DataAccessException {
		return (SubSystem)getSqlMapClientTemplate().queryForObject("System.getSystemByDeployNodeId", deployNodeId);
	}

	public void insertSystem(SubSystem system) throws DataAccessException {
		getSqlMapClientTemplate().insert("System.insertSystem", system);
		
	}

}
