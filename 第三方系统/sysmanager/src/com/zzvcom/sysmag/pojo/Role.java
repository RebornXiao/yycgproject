package com.zzvcom.sysmag.pojo;

public class Role {
	private String roleId;
	private String roleName;
	private String roleDes;
	private String createUserName;
	
	private String isAssigned;

	public Role(){
		
	}
	
	public Role(String roleId) {
		this.roleId = roleId;
	}
	
	public boolean isRootRole() {
		return this.roleId.equals("0");
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDes() {
		return roleDes;
	}
	public void setRoleDes(String roleDes) {
		this.roleDes = roleDes;
	}

	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName 要设置的 createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return isAssigned
	 */
	public String getIsAssigned() {
		return isAssigned;
	}

	/**
	 * @param isAssigned 要设置的 isAssigned
	 */
	public void setIsAssigned(String isAssigned) {
		this.isAssigned = isAssigned;
	}
	
}
