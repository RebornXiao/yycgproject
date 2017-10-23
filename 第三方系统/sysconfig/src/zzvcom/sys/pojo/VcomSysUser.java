package zzvcom.sys.pojo;

import java.util.Date;

/**
 * VcomSysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VcomSysUser implements java.io.Serializable {

	// Fields

	private String id;
	private String usercode;
	private String username;
	private String role;
	private String permissions;
	private String password;
	private String sex;
	private String phone;
	private Date createtime;
	private Date updatetime;
	private String remark;
	private String vchar1;
	private String vchar2;
	private String isstart;
	private String groupid;
	private String sysid;

	
	/** default constructor */
	public VcomSysUser() {
	}

	/** full constructor */
	public VcomSysUser(String usercode, String username, String role,
			String permissions, String password, String sex, String phone,
			Date createtime, Date updatetime, String remark, String vchar1,
			String vchar2) {
		this.usercode = usercode;
		this.username = username;
		this.role = role;
		this.permissions = permissions;
		this.password = password;
		this.sex = sex;
		this.phone = phone;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.remark = remark;
		this.vchar1 = vchar1;
		this.vchar2 = vchar2;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPermissions() {
		return this.permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVchar1() {
		return this.vchar1;
	}

	public void setVchar1(String vchar1) {
		this.vchar1 = vchar1;
	}

	public String getVchar2() {
		return this.vchar2;
	}

	public void setVchar2(String vchar2) {
		this.vchar2 = vchar2;
	}

	public String getIsstart() {
		return isstart;
	}

	public void setIsstart(String isstart) {
		this.isstart = isstart;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	

}