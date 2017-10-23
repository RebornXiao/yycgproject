package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.DeployNodeDao;
import com.zzvcom.sysmag.pojo.DeployNode;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
public class DeployNodeDaoImpl extends BaseDaoiBatis implements DeployNodeDao{

	public void deleteDeployNode(String nodeId)
			throws DataAccessException {
		getSqlMapClientTemplate().delete("DeployNode.deleteDeployNode",nodeId);
		
	}

	public PagingResultDTO getDeployNodeList(int start, int limit,String sysId)
			throws DataAccessException {
		return getPagingResultMap("DeployNode.getDeployNodeList", sysId, start, limit);
	}

	public void insertDeployNode(DeployNode deployNode)
			throws DataAccessException {
		getSqlMapClientTemplate().insert("DeployNode.insertDeploy",deployNode);
		
	}

	public void updateDeployNode(DeployNode deployNode)
			throws DataAccessException {
		getSqlMapClientTemplate().update("DeployNode.updateDeployNode",deployNode);
		
	}
	
	public DeployNode getUsingNode(String nodeId) throws DataAccessException{
		return (DeployNode)getSqlMapClientTemplate().queryForObject("DeployNode.getUsedDeployNode",nodeId);
	}
	
	public DeployNode getExist(DeployNode deployNode) throws DataAccessException{
		return (DeployNode)getSqlMapClientTemplate().queryForObject("DeployNode.getDeployNodeByName",deployNode);
	}

	public List<DeployNode> getDeployNodeList(String systemId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("DeployNode.getAllDeployNodeList", systemId);
	}

	public DeployNode getDeployNodeIdByeName(String nodename) {
		
		return (DeployNode)getSqlMapClientTemplate().queryForObject("DeployNode.getDeployNodeIdByName",nodename);
	}

	public DeployNode getDeployNodeNameById(String nodeId) {
		// TODO Auto-generated method stubnodeId  
		return (DeployNode)getSqlMapClientTemplate().queryForObject("DeployNode.getDeployNodeNameById",nodeId);
	}

}
