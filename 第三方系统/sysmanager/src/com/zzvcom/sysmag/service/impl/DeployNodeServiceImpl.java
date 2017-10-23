package com.zzvcom.sysmag.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.persistence.dao.DeployNodeDao;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.DeployNode;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.service.DeployNodeService;

@Service("deployNodeServiceService")
public class DeployNodeServiceImpl implements DeployNodeService {
	@Autowired
	private DeployNodeDao deployNodeDao;

	@Transactional(readOnly = true)
	public PagingResultDTO loadDeployNodeList(int start, int limit, String sysId) {
		return deployNodeDao.getDeployNodeList(start, limit, sysId);
	}

	@Transactional
	public void removeDeployNode(String nodeId) {
		if (this.isUsed(nodeId)) {
			throw new IllegalDeleteException(new DeployNode(nodeId));
		}
		deployNodeDao.deleteDeployNode(nodeId);

	}

	@Transactional
	public void saveDeployNode(DeployNode deployNode) {
		String nodeId = StringUtils.defaultString(deployNode.getNodeId(), "");
		if ("".equals(nodeId) || "null".equals(nodeId)) {
			deployNodeDao.insertDeployNode(deployNode);
		} else {
			deployNodeDao.updateDeployNode(deployNode);
		}
	}

	private boolean isUsed(String nodeId) {
		if (null == deployNodeDao.getUsingNode(nodeId)) {
			return false;
		} else {
			return true;
		}
	}

	public DeployNode getDeployNodeNameById(String nodeId) {
		// TODO Auto-generated method stub
		return deployNodeDao.getDeployNodeNameById(nodeId);
	}

	

}
