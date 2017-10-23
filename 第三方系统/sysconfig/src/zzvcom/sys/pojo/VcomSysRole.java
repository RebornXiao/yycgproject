package zzvcom.sys.pojo;

import java.util.Date;

/**
 * VcomSysRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VcomSysRole implements java.io.Serializable {

	// Fields

	
	private String id;
	private String rolename;
	private String permissions;
	private Integer sort;
	private Date createtime;
	private Date updatetime;
	private String remark;
	private String vchar1;
	private String type;
	private String isstart;
    private String owner;
    private String extstr;
    private String isuse;

	/** default constructor */
	public VcomSysRole() {
	}

	/** full constructor */
	public VcomSysRole(String rolename, String permissions, Integer sort,
			Date createtime, Date updatetime, String remark, String type,String vchar1) {
		this.rolename = rolename;
		this.permissions = permissions;
		this.sort = sort;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.remark = remark;
		this.type=type;
		this.vchar1 = vchar1;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getPermissions() {
		return this.permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsstart() {
		return isstart;
	}

	public void setIsstart(String isstart) {
		this.isstart = isstart;
	}

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getExtstr()
    {
        return extstr;
    }

    public void setExtstr(String extstr)
    {
        this.extstr = extstr;
    }

	public String getIsuse() {
		return isuse;
	}

	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
	

}