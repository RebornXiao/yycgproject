package com.zzvcom.sysmag.pojo;

/**
 * 区域实体类
 * @author lihaiqing
 * 2012-08-06 李海青修改，添加业务级别
 */
public class Area {
	private String areaId;
	private String areaName;
	private int areaLevel;
	private String fullName;
	private String shortName;
	private String upperAreaId;
	private String upperAreaName;
	private String isUnit = "0";
	private String lastUpdateTime;
	private String yzCode;
	private String telecomCode;
	
	private String showOrder;
	private String businessLevel;
	
	
	public Area() {
		
	}
	
	public Area(String areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getAreaLevel() {
		return areaLevel;
	}
	public void setAreaLevel(int areaLevel) {
		this.areaLevel = areaLevel;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getUpperAreaId() {
		return upperAreaId;
	}
	public void setUpperAreaId(String upperAreaId) {
		this.upperAreaId = upperAreaId;
	}
	public String getIsUnit() {
		return isUnit;
	}
	public void setIsUnit(String isUnit) {
		this.isUnit = isUnit;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Area) {
			Area area = (Area)obj;
			if(this.areaId.equals(area.getAreaId())){
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
	public String getUpperAreaName() {
		return upperAreaName;
	}
	public void setUpperAreaName(String upperAreaName) {
		this.upperAreaName = upperAreaName;
	}

	public String getYzCode() {
		return yzCode;
	}

	public void setYzCode(String yzCode) {
		this.yzCode = yzCode;
	}

	public String getTelecomCode() {
		return telecomCode;
	}

	public void setTelecomCode(String telecomCode) {
		this.telecomCode = telecomCode;
	}

	/**
	 * @return showOrder
	 */
	public String getShowOrder() {
		return showOrder;
	}

	/**
	 * @param showOrder 要设置的 showOrder
	 */
	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	/**
	 * @return businessLevel
	 */
	public String getBusinessLevel() {
		return businessLevel;
	}

	/**
	 * @param businessLevel 要设置的 businessLevel
	 */
	public void setBusinessLevel(String businessLevel) {
		this.businessLevel = businessLevel;
	}
	
	
}
