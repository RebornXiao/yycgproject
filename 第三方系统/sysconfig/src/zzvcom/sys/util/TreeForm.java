package zzvcom.sys.util;

import java.util.ArrayList;
import java.util.List;

public class TreeForm {
	private String id;
	private String text;
	private String href;
	private List children=new ArrayList();
	private Boolean  leaf;
	private String parentid;
	private String icon="../images/icon-1.png";
	private Boolean toclick=false;
	private String toopen;
	private String fileforder;
	private String fileforderid;
	private String topfiletype;
	private Boolean checked;
	
	public String getTopfiletype() {
		return topfiletype;
	}
	public void setTopfiletype(String topfiletype) {
		this.topfiletype = topfiletype;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getFileforder() {
		return fileforder;
	}
	public void setFileforder(String fileforder) {
		this.fileforder = fileforder;
	}
	public String getFileforderid() {
		return fileforderid;
	}
	public void setFileforderid(String fileforderid) {
		this.fileforderid = fileforderid;
	}
	public String getToopen() {
		return toopen;
	}
	public void setToopen(String toopen) {
		this.toopen = toopen;
	}
	public Boolean getToclick() {
		return toclick;
	}
	public void setToclick(Boolean toclick) {
		this.toclick = toclick;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
}
