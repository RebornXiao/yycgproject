package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.Role;

/**
 * 模块数据访问对象接口
 * @author Wang Xiaoming
 */
public interface ModuleDao {
	public void	insertModule(Module module) throws DataAccessException;
	public void updateModule(Module module) throws DataAccessException;
	public void deleteModule(String moduleId) throws DataAccessException;
	
	public Module getModuleById(String moduleId) throws DataAccessException;
	public List<Module> getModuleByName(String moduleName) throws DataAccessException;
	
	public List<Module> getModuleList(String systemId, String upperModuleId) throws DataAccessException;
	public List<Module> getModuleList(Role role, String systemId, String deployNodeId, String upperModuleId) throws DataAccessException;
	
	public List<Module> getUsedModuleList(String moduleId) throws DataAccessException;
	
	public void saveModuleOrder(Module module) throws DataAccessException;
}
