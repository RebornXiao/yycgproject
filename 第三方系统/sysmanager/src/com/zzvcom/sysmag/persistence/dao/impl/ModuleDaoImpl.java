package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.ModuleDao;
import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.Role;

public class ModuleDaoImpl extends BaseDaoiBatis implements ModuleDao {

	public void deleteModule(String moduleId) throws DataAccessException {
		getSqlMapClientTemplate().delete("Module.deleteModule", moduleId);
	}

	public Module getModuleById(String moduleId) throws DataAccessException {
		return (Module)getSqlMapClientTemplate().queryForObject("Module.getModuleById", moduleId);
	}

	public List<Module> getModuleByName(String moduleName) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Module.getModuleByName", moduleName);
	}

	public void insertModule(Module module) throws DataAccessException {
		getSqlMapClientTemplate().insert("Module.insertModule", module);
	}

	public void updateModule(Module module) throws DataAccessException {
		getSqlMapClientTemplate().update("Module.updateModule", module);
	}

	public List<Module> getModuleList(String systemId, String upperModuleId)
			throws DataAccessException {
		Map params = new HashMap();
		params.put("systemId", systemId);
		params.put("upperModuleId", upperModuleId);
		return getSqlMapClientTemplate().queryForList("Module.getModuleListForAdmin", params);
	}

	public List<Module> getModuleList(Role role, String systemId, String deployNodeId,
			String upperModuleId) throws DataAccessException {
		Map params = new HashMap();
		params.put("roleId", role.getRoleId());
		params.put("systemId", systemId);
		params.put("deployNodeId", deployNodeId);
		params.put("upperModuleId", upperModuleId);
		return getSqlMapClientTemplate().queryForList("Module.getModuleListForCommon", params);
	}

	public List<Module> getUsedModuleList(String moduleId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Module.getUsedModuleList", moduleId);
	}

	public void saveModuleOrder(Module module)
			throws DataAccessException {
		getSqlMapClientTemplate().update("Module.saveOrder", module);
		
	}

}
