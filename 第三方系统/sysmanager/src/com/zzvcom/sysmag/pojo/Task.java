package com.zzvcom.sysmag.pojo;

/**
 * 数据同步任务实体类
 * @author Wang Xiaoming
 */
public class Task {
	public static final String OPT_TYPE_ADD = "A";
	public static final String OPT_TYPE_EDIT = "E";
	public static final String OPT_TYPE_DEL = "D";
	
	private String id;
	private String optUserName; //创建任务的用户
	private String serviceType;
	private String operationType;
	private String priValue;
	private String priKey;
	private String createTime;
	private String execTime;
	private String status = "NEXC";
	private int failCount = 0;
	private String address;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getPriValue() {
		return priValue;
	}
	public void setPriValue(String priValue) {
		this.priValue = priValue;
	}
	public String getPriKey() {
		return priKey;
	}
	public void setPriKey(String priKey) {
		this.priKey = priKey;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getExecTime() {
		return execTime;
	}
	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public String getOptUserName() {
		return optUserName;
	}
	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}
	
	
}
