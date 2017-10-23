package zzvcom.sys.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * VcomSysModule entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VcomSysModule implements java.io.Serializable {

	// Fields

	private String id;
	private String modulename;
	private Integer depth;
	private Integer sort;
	private String link;
	private String parentid;
	private String remark;
	private Date createtime;
	private Date updatetime;
	private List children=new ArrayList();
	
	// Constructors

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	/** default constructor */
	public VcomSysModule() {
	}

	/** full constructor */
	public VcomSysModule(String modulename, Integer depth, Integer sort,
			String link, String parentid, String remark, Date createtime,
			Date updatetime) {
		this.modulename = modulename;
		this.depth = depth;
		this.sort = sort;
		this.link = link;
		this.parentid = parentid;
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

	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
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