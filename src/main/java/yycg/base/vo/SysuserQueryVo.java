package yycg.base.vo;

import yycg.base.po.Sysuser;

/**
 * 此对象是用于用户管理传递参数使用
 * @author Thinkpad
 *
 */
public class SysuserQueryVo {
	
	private PageQuery pageQuery ;//用于分页查询
	private Sysuser sysuser;//用于添加信息提交
	private SysuserCustom sysuserCustom;//用于信息显示，查询条件提交

	public Sysuser getSysuser() {
		return sysuser;
	}

	public void setSysuser(Sysuser sysuser) {
		this.sysuser = sysuser;
	}

	public PageQuery getPageQuery() {
		return pageQuery;
	}

	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}

	public SysuserCustom getSysuserCustom() {
		return sysuserCustom;
	}

	public void setSysuserCustom(SysuserCustom sysuserCustom) {
		this.sysuserCustom = sysuserCustom;
	}
	
	

}
