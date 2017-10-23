package com.zzvcom.sysmag.service;

import java.util.List;

import com.zzvcom.sysmag.pojo.DeployNode;
import com.zzvcom.sysmag.pojo.PagingResultDTO;

/**
 * 节点管理Service接口
 * @author maohuiping
 * 
 */
public interface DeployNodeService {

	public PagingResultDTO loadDeployNodeList(int start, int limit, String sysId);

	public void saveDeployNode(DeployNode deployNode);

	public void removeDeployNode(String nodeId);
	public DeployNode getDeployNodeNameById(String nodeId);
	
}
