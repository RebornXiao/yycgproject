package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.TaskRecorder;
import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.Task;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.util.PublicTools;

public class TaskRecorderImpl extends BaseDaoiBatis implements TaskRecorder {
	/*
	 * 苗润土屏蔽实现方法，不用同步功能
	public void record(User loginUser, User user, String optType) {
		Task task = new Task();
		task.setServiceType("user_set");
		task.setOptUserName(loginUser.getUserName());
		task.setOperationType(optType);
		task.setPriValue(user.getUserName());
		task.setCreateTime(PublicTools.getCurtimeOfString("yyyyMMddHHmmss"));
		getSqlMapClientTemplate().insert("Task.insertTask", task);
	}

	public void record(User loginUser, Area area, String optType) {
		Task task = new Task();
		task.setServiceType("area_set");
		task.setOptUserName(loginUser.getUserName());
		task.setOperationType(optType);
		task.setPriValue(area.getAreaId());
		task.setCreateTime(PublicTools.getCurtimeOfString("yyyyMMddHHmmss"));
		getSqlMapClientTemplate().insert("Task.insertTask", task);
	}

	public void record(User loginUser, Business business, String optType) {
		Task task = new Task();
		task.setServiceType("business_set");
		task.setOptUserName(loginUser.getUserName());
		task.setOperationType(optType);
		task.setPriValue(business.getBusinessCode());
		task.setCreateTime(PublicTools.getCurtimeOfString("yyyyMMddHHmmss"));
		getSqlMapClientTemplate().insert("Task.insertTask", task);
	}

	public void record(User loginUser, Operation operation, String optType) {
		Task task = new Task();
		task.setServiceType("operation_set");
		task.setOptUserName(loginUser.getUserName());
		task.setOperationType(optType);
		task.setPriValue(operation.getOperationId());
		task.setCreateTime(PublicTools.getCurtimeOfString("yyyyMMddHHmmss"));
		getSqlMapClientTemplate().insert("Task.insertTask", task);
	}

	@Transactional
	public void record(User loginUser, User user, List oldNodeList, List newNodeList) {
		Map<String,List> result = seperateNodeList(oldNodeList, newNodeList);
		
		if (result.get("add").size() != 0) {
			record(loginUser, user, Task.OPT_TYPE_ADD, result.get("add"));
		}
		
		//如果更改前和更改后存在相同的节点，则保存相同的部分
		if (result.get("edit").size() != 0) {
			record(loginUser, user, Task.OPT_TYPE_EDIT, result.get("edit"));
		}
		
		if (result.get("delete").size() != 0) {
			record(loginUser, user, Task.OPT_TYPE_DEL, result.get("delete"));
		}
	}
	

	public void record(User loginUser, User user, String optType, List nodeList) {
		Task task = new Task();
		task.setServiceType("user_set");
		task.setOptUserName(loginUser.getUserName());
		task.setOperationType(optType);
		task.setPriValue(user.getUserName());
		task.setCreateTime(PublicTools.getCurtimeOfString("yyyyMMddHHmmss"));
		task.setAddress(concatListElementToString(nodeList));
		getSqlMapClientTemplate().insert("Task.insertTask", task);
		
	}
	*/
	
	public void record(User loginUser, User user, String optType){}
	public void record(User loginUser, User user, List oldNodeList, List newNodeList){}
	public void record(User loginUser, User user, String optType, List nodeList){}
	public void record(User loginUser, Area area, String optType){}
	public void record(User loginUser, Business business, String optType){}
	public void record(User loginUser, Operation operation, String optType){}

	/**
	 * 将List的元素用","连接
	 * @return 
	 */
	private String concatListElementToString(List<String> list){
		StringBuilder result = new StringBuilder("");
		for (String element : list) {
			result.append(element).append(",");
		}
		if (result.length() < 1) {
			return "";
		}
		return result.substring(0, result.length()-1);
	}
	
	/**
	 * 对比新旧节点id列表，并分离新增、未改动、删除
	 * @param oldNodeList
	 * @param newNodeList
	 * @return
	 */
	private Map seperateNodeList(List<String> oldNodeList, List<String> newNodeList){
		List<String> additionList = new ArrayList<String>();
		List<String> editionList = new ArrayList<String>();
		List<String> deletionList  = new ArrayList<String>();
		
		for (String nodeId : oldNodeList) {
			if (newNodeList.contains(nodeId)) {
				editionList.add(nodeId);
			} else {
				deletionList.add(nodeId);
			}
		}
		
		for (String nodeId : newNodeList) {
			if (!oldNodeList.contains(nodeId)) {
				additionList.add(nodeId);
			}
		}
		
		Map resultMap = new HashMap<String, List>();
		resultMap.put("add", additionList);
		resultMap.put("edit", editionList);
		resultMap.put("delete", deletionList);
		
		return resultMap;
	}
	
	public static void main(String[] args) {
		List<String> oldNodeList = new ArrayList<String>();
		List<String> newNodeList = new ArrayList<String>();
		oldNodeList.add("1");
		oldNodeList.add("2");
		oldNodeList.add("3");
		
		newNodeList.add("2");
		newNodeList.add("3");
		newNodeList.add("4");
		
		TaskRecorderImpl taskRecorder = new TaskRecorderImpl();
		/*Map resultMap = taskRecorder.seperateNodeList(oldNodeList, newNodeList);
		System.out.println(resultMap);
		System.out.println(taskRecorder.concatListElementToString(oldNodeList));*/
	}
}
