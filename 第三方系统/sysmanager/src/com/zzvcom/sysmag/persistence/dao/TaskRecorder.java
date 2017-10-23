package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.User;

public interface TaskRecorder {
	public void record(User loginUser, User user, String optType);
	public void record(User loginUser, User user, List oldNodeList, List newNodeList);
	public void record(User loginUser, User user, String optType, List nodeList);
	public void record(User loginUser, Area area, String optType);
	public void record(User loginUser, Business business, String optType);
	public void record(User loginUser, Operation operation, String optType);
}
