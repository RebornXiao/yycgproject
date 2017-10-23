package zzvcom.sys.dao;

import java.util.List;

import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.util.PageUtil;

/**
 * 用户接口
 * 
 * @author LHQ.
 * 
 */
public interface ModuleDao extends GenericDao<VcomSysModule> {

	/**
	 * 在parentid下是否包含相同的模块名称
	 * 
	 * @param parentid
	 * @param modulename
	 * @param exceptid
	 * @return
	 */
	public boolean isContainModulename(String parentid, String modulename,
			String exceptid);

	/**
	 * 在parentid下是否包含相同的模块名称
	 * 
	 * @param parentid
	 * @param modulename
	 * 
	 * @return
	 */
	public boolean isContainModulename(String parentid, String modulename);

	/**
	 * 根据parentid分页获取模块信息
	 * 
	 * @param parentid
	 *            父id
	 * @param modulename
	 *            模块（菜单）名称
	 * @param page
	 * @return
	 */
	public List<VcomSysModule> getModuleListByParentid(String parentid,
			String modulename, PageUtil page);

	/**
	 * 获得所有的模块列表
	 * 
	 * @return
	 */
	public List<VcomSysModule> getModuleList();
	/**
	 * 获取模块列表
	 * 
	 * @param inIds
	 *            包括的id字符串，如 "1,2,3"
	 * @param notinIds
	 *            不包括的id字符串，如 "1,2,3"
	 * @return
	 */
	public List<VcomSysModule> getModuleListByIds(String inIds, String notinIds);
	
	/**
	 * 
	  * @函数介绍：根据父结点id查询所有子模块列表（不分页）
	  * @参数：
	  * @返回值：
	 */
	public List<VcomSysModule> getAllModuleListByParentid(String parentid); 
}
