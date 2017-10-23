package com.zzvcom.sysmag.pojo;

public class DeployNode {
	private String  nodeId;
	private String  sysId;
	private int showOrder;
	private String nodeName;
	private String  nodeUrl;
	private String  sysName;
	private String nodeIcon;
	
	public DeployNode(){}
	public DeployNode(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	public String getNodeUrl() {
		return nodeUrl;
	}
	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}
	public String getNodeIcon() {
		return nodeIcon;
	}
	public void setNodeIcon(String nodeIcon) {
		String icon = nodeIcon;
		if (nodeIcon == null || nodeIcon.equals("")) {
			icon = "image/icons/server.png";
		}
		this.nodeIcon = icon;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
