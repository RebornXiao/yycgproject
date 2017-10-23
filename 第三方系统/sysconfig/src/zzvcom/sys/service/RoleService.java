package zzvcom.sys.service;

import java.util.List;

import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.util.ReMsg;
import zzvcom.util.PageUtil;

public interface RoleService
{
	/**
	 * 新增用户接口
	 * @param role
	 * @throws Exception
	 */
	public String save(VcomSysRole role) throws SaveOrUpdateException;
	/**
	 * 修改用户接口
	 * @param role
	 * @throws Exception
	 */
	public void saveOrupdate(VcomSysRole role) throws SaveOrUpdateException;

	/**
	 * 删除用户接口
	 * @param role
	 * @throws Exception
	 */
	public void delete(VcomSysRole role) throws DeleteException;
	/**
	 * 根据ID获取特定用户接口
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public VcomSysRole query(String id) throws ObjectNotFindException;
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
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ReMsg deleteRole(String id) throws ObjectNotFindException, DeleteException;

	/**
	 * 保存角色
	 * 
	 * @param roleObj
	 * @return
	 * @throws Exception
	 */
	public ReMsg saveRole(VcomSysRole  roleObj) throws SaveOrUpdateException;

	/**
	 * 修改角色
	 * 
	 * @param roleObj
	 * @return
	 * @throws Exception
	 */
	public ReMsg updateRole(VcomSysRole roleObj) throws SaveOrUpdateException;
	
	public boolean checkRoleCode(String roleCode)throws ObjectNotFindException;
    public boolean checkRoleIsUsed(String roleCode)throws ObjectNotFindException;
}
