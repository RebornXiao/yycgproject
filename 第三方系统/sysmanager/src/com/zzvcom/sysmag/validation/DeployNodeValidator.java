package com.zzvcom.sysmag.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.persistence.dao.DeployNodeDao;
import com.zzvcom.sysmag.pojo.DeployNode;
@Component("deployNodeValidator")
public class DeployNodeValidator implements Validator{
	@Autowired
	private DeployNodeDao deployNodeDao;
	private DeployNode rsltNode;
	private DeployNode vsltNode;
	private Errors errors;
	
	public Errors validate(Object validateObj) {
	errors = new Errors();
	vsltNode = (DeployNode) validateObj;
	validatNodeName();
	return errors;
	}
	
	private void validatNodeName() {
	rsltNode =deployNodeDao.getExist(vsltNode);
		if(rsltNode != null) {
			if (!(rsltNode.getNodeId()).equals(vsltNode.getNodeId())) {
				errors.reject("nodeName", MessageInfo.EXSITS_BUSINESSNAME);
			}
		}
	}
}
