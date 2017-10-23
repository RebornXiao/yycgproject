package com.zzvcom.sysmag.pojo;

/**
 * 权限节点对象(类型+ID)
 * @author Wang Xiaoming
 */
public class AuthNode {
	private String type;
	private String id;
	
	public AuthNode(){
		
	}
	
	public AuthNode(String str){
		this.type = str.substring(0, 1);
		this.id = str.substring(1, str.length());
	}
	
	public boolean isSystem() {
		return this.type.equals("s");
	}
	
	public boolean isDeployNode() {
		return this.type.equals("n");
	}
	
	public boolean isTopModule() {
		return this.type.equals("t");
	}
	
//	public boolean isMiddleModule() {
//		return this.type.equals("m");
//	}
	
	public boolean isBottomModule() {
		return this.type.equals("b");
	}
	
	public boolean isOperation() {
		return this.type.equals("o");
	}
	
	@Override
	public String toString() {
		return this.type+this.id;
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
