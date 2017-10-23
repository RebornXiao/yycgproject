package com.zzvcom.sysmag.pojo;

public class SubSystem {
	private String systemId;
	private String systemName;
	private String url;
	private String icon;
	private int showOrder;
	private SystemAssignStatus assignStatus;
	

	public SystemAssignStatus getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(SystemAssignStatus assignStatus) {
		this.assignStatus = assignStatus;
	}

	public SubSystem(){
		
	}
	
	public SubSystem(String systemId) {
		this.systemId = systemId;
	}
	
	public SubSystem(String systemId, String systemName) {
		this.systemId = systemId;
		this.systemName = systemName;
	}
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	
}
