package com.zzvcom.sysmag.pojo;

/**
 * 业务实体类
 * @author Wang Xiaoming
 */
public class Business {
	private String businessId;
	private String businessCode;
	private String businessName;
	private int showOrder;
	private String remark;

	public static final String THE_ALL_BUSINESS_CODE = "0";
	
	public String getBusinessId() {
		return businessId;
	}
	
	public Business() {
	}
	
	public Business(String businessId) {
		this.businessId = businessId;
	}
	
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Business) {
			Business cmpBusiness = (Business) obj;
			if (this.businessId.equals(cmpBusiness.getBusinessId())){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
