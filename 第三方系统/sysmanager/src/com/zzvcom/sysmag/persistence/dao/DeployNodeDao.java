package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.DeployNode;
import com.zzvcom.sysmag.pojo.PagingResultDTO;

public interface DeployNodeDao {
	
	public PagingResultDTO getDeployNodeList(int start, int limit ,String sysId) throws DataAccessException;
	
	public void insertDeployNode(DeployNode deployNode) throws DataAccessException;
	public void updateDeployNode(DeployNode deployNode) throws DataAccessException;
	public void deleteDeployNode(String nodeId) throws DataAccessException;
	public DeployNode getUsingNode(String nodeId) throws DataAccessException;
	public DeployNode getExist(DeployNode deployNode) throws DataAccessException;
	public DeployNode getDeployNodeIdByeName(String nodename);
	public DeployNode getDeployNodeNameById(String nodeId);

	public List<DeployNode> getDeployNodeList(String systemId) throws DataAccessException;
	
}
