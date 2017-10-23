package com.zzvcom.sysmag.pojo;

import java.util.ArrayList;
import java.util.List;

public class User {
	private static final String ADMIN_NAME = "admin";
	
	private String userName;
	private String trueName;
	private String password;
	private String contact;
	private String address;
	private String email;
	private String userState;
	private String remark;
	private String createTime;
	private String sex;
	private String phone;
	private String movePhone;
	private String fax;
	private String lastUpdate;
	
	private List<Area> areaList;
	private List<Business> businessList;
	private Role role;
	
	public User(){
		
	}
	
	public User(String userName) {
		this.userName = userName;
	}
	
	public boolean isAdmin() {
		Role role = getRole();
		if (null == role) {
			return false;
		}
		return role.isRootRole();
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMovePhone() {
		return movePhone;
	}
	public void setMovePhone(String movePhone) {
		this.movePhone = movePhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}
	
	public void setAreaList(String areaIds) {
		String[] areaIdArray = areaIds.split(",");
		List<Area> areaList = new ArrayList<Area>();
		for (int i = 0; i < areaIdArray.length; i++) {
			Area area = new Area(areaIdArray[i]);
			areaList.add(area);
		}
		this.areaList = areaList;
	}

	public List<Business> getBusinessList() {
		return businessList;
	}

	public void setBusinessList(List<Business> businessList) {
		this.businessList = businessList;
	}
	
	public void setBusinessList(String bussIds) {
		String[] bussArray = bussIds.split(",");
		List<Business> bussList = new ArrayList<Business>();
		for (int i = 0; i < bussArray.length; i++) {
			Business business = new Business(bussArray[i]);
			bussList.add(business);
		}
		this.businessList = bussList;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
}
