package yycg.base.vo;

import yycg.base.po.Sysuser;

public class SysuserCustom extends Sysuser {
    private String groupname;//用户类型的名称
    private String sysmc;//单位名称

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getSysmc() {
		return sysmc;
	}

	public void setSysmc(String sysmc) {
		this.sysmc = sysmc;
	}
    
    
    
}
