package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.OperationDao;
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.Role;

public class OperationDaoImpl extends BaseDaoiBatis implements OperationDao {

	public void deleteOperation(String operationId)  throws DataAccessException {
		getSqlMapClientTemplate().delete("Operation.deleteOperation", operationId);
	}

	public Operation getOperationById(String operationId)   throws DataAccessException{
		return (Operation)getSqlMapClientTemplate().queryForObject("Operation.getOperationById", operationId);
	}

	public List<Operation> getOperationByName(String operationName)  throws DataAccessException{
		return getSqlMapClientTemplate().queryForList("Operation.getOperationByName", operationName);
	}

	public List<Operation> getOperationList(String bottomModuleId)  throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Operation.getOperationListForAdmin", bottomModuleId);
	}

	public List<Operation> getOperationList(Role role, String bottomModuleId)  throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertOperation(Operation operation)  throws DataAccessException {
		getSqlMapClientTemplate().insert("Operation.insertOperation", operation);
	}

	public void updateOperation(Operation operation)  throws DataAccessException {
		getSqlMapClientTemplate().update("Operation.updateOperation", operation);

	}

	public List<Operation> getUsedOperationList(String operationId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Operation.getUsedOperationList", operationId);
	}

	public List<Operation> getOperationListByIds(List<String> operationIdList)
			throws DataAccessException {
		Map params = new HashMap();
		params.put("operationIdList", operationIdList);
		return getSqlMapClientTemplate().queryForList("Operation.getOperationListByIds", params);
	}

	public void saveOperationOrder(Operation operation)
			throws DataAccessException {
		getSqlMapClientTemplate().update("Operation.saveOrder", operation);
	}
	
	public void deleteOperationByModuleid(String moduleId)  throws DataAccessException {
		getSqlMapClientTemplate().delete("Operation.deleteOperationByModuleid", moduleId);
	}

}
