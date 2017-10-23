package com.zzvcom.sysmag.pojo;

public class MenuElement {
	public static final String ROOT = "root";
	public static final String SYSTEM_TYPE = "s";
	public static final String TOP_MODULE_TYPE = "t";
//	public static final String MIDDLE_MODULE_TYPE = "m";//new
	public static final String BOTTOM_MODULE_TYPE = "b";
	public static final String OPERATION_TYPE = "o";
	
	private String type;
	private String id;
	public MenuElement(String menuStr) {
		if(menuStr.equals(ROOT)) {
			this.type = ROOT;
			this.id = "";
		}else {
			this.type = menuStr.substring(0, 1);
			this.id = menuStr.substring(1, menuStr.length());
		}

	}
	
	public boolean isRoot() {
		return this.type.equals(ROOT);
	}
	
	public boolean isSystem(){
		return this.type.equals(SYSTEM_TYPE);
	}
	
	public boolean isTopModule() {
		return this.type.equals(TOP_MODULE_TYPE);
	}
	
//	public boolean isMiddleModule() {
//		return this.type.equals(MIDDLE_MODULE_TYPE);
//	}
	
	public boolean isBottomModule() {
		return this.type.equals(BOTTOM_MODULE_TYPE);
	}
	
	public boolean isOperation() {
		return this.type.equals(OPERATION_TYPE);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
