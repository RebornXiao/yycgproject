package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.Role;

/**
 * 操作功能数据访问对象接口
 * @author Wang Xiaoming
 */
public interface OperationDao {
	public Operation getOperationById(String operationId) throws DataAccessException;
	public List<Operation> getOperationByName(String operationName)  throws DataAccessException;
	
	public void insertOperation(Operation operation)  throws DataAccessException;
	public void updateOperation(Operation operation)  throws DataAccessException;
	public void deleteOperation(String operationId)  throws DataAccessException;
	
	public List<Operation> getOperationList(String bottomModuleId)  throws DataAccessException;
	public List<Operation> getOperationList(Role role, String bottomModuleId)  throws DataAccessException;
	
	public List<Operation> getOperationListByIds(List<String> operationIdList) throws DataAccessException;
	
	public List<Operation> getUsedOperationList(String operationId)  throws DataAccessException;
	
	public void saveOperationOrder(Operation operation) throws DataAccessException;
	
	
	
	public void deleteOperationByModuleid(String moduleId)  throws DataAccessException;
}
