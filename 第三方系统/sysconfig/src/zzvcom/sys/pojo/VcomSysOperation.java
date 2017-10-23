package zzvcom.sys.pojo;

import java.util.Date;

/**
 * VcomSysOperation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VcomSysOperation implements java.io.Serializable {

	// Fields

	private String id;
	private String opername;
	private String moduleid;
	private String opercode;
	private String method;
	private Integer sort;
	private String remark;
	private Date createtime;
	private Date updatetime;

	// Constructors

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/** default constructor */
	public VcomSysOperation() {
	}

	/** full constructor */
	public VcomSysOperation(String opername, String moduleid, String opercode,
			String method, String remark, Date createtime, Date updatetime) {
		this.opername = opername;
		this.moduleid = moduleid;
		this.opercode = opercode;
		this.method = method;
		this.remark = remark;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpername() {
		return this.opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public String getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getOpercode() {
		return this.opercode;
	}

	public void setOpercode(String opercode) {
		this.opercode = opercode;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}