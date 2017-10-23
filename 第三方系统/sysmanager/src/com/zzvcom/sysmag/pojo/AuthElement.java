package com.zzvcom.sysmag.pojo;

/**
 * 权限元素对象(类型+ID-类型+ID-...-类型+ID)
 * @author Wang Xiaoming
 */
public class AuthElement {
	private String[] items;
	
	public AuthElement() {

	}

	public AuthElement(String item) {
		this.items = item.split("-");
	}
	
	public AuthNode getFirstNode() {
		return new AuthNode(items[0]);
	}
	
	public AuthNode getLastNode() {
		return new AuthNode(items[items.length-1]);
	}
	
}
