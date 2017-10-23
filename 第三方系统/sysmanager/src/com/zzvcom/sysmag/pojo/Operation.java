package com.zzvcom.sysmag.pojo;

public class Operation {
	private String operationId;
	private String operationName;
	private String operationCode;
	private String moduleId;
	private String moduleName;
	private String method;
	private String icon;
	private int showOrder;
	
	public Operation(){
		
	}
	
	public boolean inSameModuleWith(Operation opt) {
		return this.moduleId.equals(opt.getModuleId());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Operation) {
			Operation operation = (Operation)obj;
			if(this.operationId.equals(operation.getOperationId())){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Operation(String operationId) {
		this.operationId = operationId;
	}
	
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		String _icon = icon;
		if (icon == null || icon.equals("")) {
			_icon = "image/class.gif";
		}
		this.icon = _icon;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
}
