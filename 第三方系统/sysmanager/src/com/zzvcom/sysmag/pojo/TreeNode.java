package com.zzvcom.sysmag.pojo;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String id;            //ID
    private String text;          //节点显示
    private String cls;           //样式
    private String iconCls;        //图标样式
    private String qtip;          //提示信息 
    private String icon;		  //图标
    private boolean leaf;         //是否叶子
    private String href;          //链接
    private boolean checked;      //是否选中
    private String hrefTarget;    //链接指向
    private boolean expandable;   //是否展开
    private String description;   //描述信息
    private List<TreeNode> children;
    
    public TreeNode() {
    	children = new ArrayList<TreeNode>();
    }
    
    public TreeNode(String id, String text) {
    	this.id = id;
    	this.text = text;
    	children = new ArrayList<TreeNode>();
    }

    public void addChild(TreeNode child) {
    	this.children.add(child);
    }
    
    public boolean hasChild() {
    	return this.children.size() != 0;
    }

	public void setChildren(List<TreeNode> children) {
		this.children = children;
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
    public String getCls() {
        return cls;
    }
    public void setCls(String cls) {
        this.cls = cls;
    }
    public boolean isLeaf() {
        return leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public String getHrefTarget() {
        return hrefTarget;
    }
    public void setHrefTarget(String hrefTarget) {
        this.hrefTarget = hrefTarget;
    }
    public boolean isExpandable() {
        return expandable;
    }
    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
    
    
}
