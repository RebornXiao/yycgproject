package zzvcom.sys.dao;

import java.util.List;

import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.util.PageUtil;
/**
 * 用户接口
 * 
 * @author LHQ.
 *
 */
public interface RoleDao extends GenericDao<VcomSysRole>
{
	
	/**
	 * 根据分页获取用户信息
	 * @param page
	 * @return
	 */
	public List getAllBySplitPage(PageUtil page,VcomSysRole role);
	/**
	 * 获取所有的角色
	 * @return
	 */
	public List getAllRole();
	/**
	 * 更加角色id获取集合
	 * @param ids
	 * @return
	 */
	public List getRoleByIds(String ids);
	
	/**
	 * 否包含相同的角色名称
	 * 
	 * @param rolename
	 * @param exceptid
	 * 
	 * @return
	 */
	public boolean isContainRolename(String rolename,long exceptid);
	/**
	 * 否包含相同的角色名称
	 * 
	 * @param rolename
	 * 
	 * @return
	 */
	public boolean isContainRolename(String rolename);
    
    public boolean isUsedRole(String roleid);
}
