package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;

/**
 * 角色数据访问接口
 * @author Wang Xiaoming
 */
public interface RoleDao {
	public void insertRole(Role role) throws DataAccessException;
	public void updateRole(Role role) throws DataAccessException;
	public void deleteRole(String roleId) throws DataAccessException;
	
	/**
	 * 根据角色Id读取角色信息
	 * @param roleId 角色Id
	 * @return 角色
	 * @throws DataAccessException
	 */
	public Role getRoleById(String roleId) throws DataAccessException;
	
	/**
	 * 根据角色名称读取角色信息
	 * @param roleName 角色名称
	 * @return 角色
	 * @throws DataAccessException
	 */
	public Role getRoleByName(String roleName) throws DataAccessException;
	
	/**
	 * 读取特定用户所属的角色信息
	 * @param userName 用户名
	 * @return 角色
	 * @throws DataAccessException
	 */
	public Role getUserRole(String userName) throws DataAccessException;
	
	/**
	 * 读取所有角色信息列表
	 * 通常供<b>系统超级管理员</b>使用
	 * @return 角色
	 * @throws DataAccessException
	 */
	public List<Role> getRoleList() throws DataAccessException;
	
	/**
	 * 分页读取所有角色信息列表<br>
	 * 通常供<b>系统超级管理员</b>使用
	 * @param start 开始记录
	 * @param limit 每页记录数
	 * @return 角色列表
	 * @throws DataAccessException
	 */
	public PagingResultDTO getRoleList(int start, int limit) throws DataAccessException;
	
	
	/**
	 * 分页读取特定用户访问权限内的角色信息列表<br>
	 * 角色的区域<b>低于或平级于</b>用户的管理区域
	 * @param userName 用户名
	 * @param start 开始记录
	 * @param limit 每页记录数
	 * @return 角色列表
	 * @throws DataAccessException
	 */
	public PagingResultDTO getRoleList(String userName, int start, int limit) throws DataAccessException;
	
	/**
	 * 用户添加或修改时加载角色列表<br>
	 * @param userName 用户名
	 * @return 角色列表
	 * @throws DataAccessException
	 */
	public List<Role> getAddOrEditRoleList(String userName, String cmd, String edituser) throws DataAccessException;
	
	/**
	 * 读取用户访问权限内的角色信息列表<br>
	 * @param userName 用户名
	 * @return 角色列表
	 * @throws DataAccessException
	 */
	public List<Role> getUserControlRoleList(String userName) throws DataAccessException;
	
	/**
	 * 读取正在被用户使用的角色列表
	 * @param roleId 角色Id
	 * @return 角色列表
	 * @throws DataAccessException
	 */
	public List<Role> getUserUsingRoleList(String roleId) throws DataAccessException;
}
