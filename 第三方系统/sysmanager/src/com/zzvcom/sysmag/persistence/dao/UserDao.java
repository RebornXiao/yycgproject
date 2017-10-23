package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.User;
/**
 * 用户数据访问对象接口
 * @author Wang Xiaoming
 */
public interface UserDao {
	public void insertUser(User user) throws DataAccessException;
	public void updateUser(User user) throws DataAccessException;
	public void deleteUser(String userName) throws DataAccessException;
	
	/**
	 * 保存用户和区域的关联关系
	 * @param userName 用户名
	 * @param areaId 区域Id
	 * @throws DataAccessException
	 */
	public void saveUserArea(String userName, String areaId) throws DataAccessException;
	
	/**
	 * 保存用户和角色的关联关系
	 * @param userName 用户名
	 * @param roleId 角色Id
	 * @throws DataAccessException
	 */
	public void saveUserRole(String userName, String roleId) throws DataAccessException;
	
	/**
	 * 保存用户和业务的关联关系
	 * @param userName 用户名
	 * @param businessId 业务Id
	 * @throws DataAccessException
	 */
	public void saveUserBusiness(String userName, String businessId) throws DataAccessException;
	
	/**
	 * 删除用户和区域的关联关系
	 * @param userName 用户名
	 * @throws DataAccessException
	 */
	public void deleteUserArea(String userName) throws DataAccessException;
	
	/**
	 * 删除用户和角色的关联关系
	 * @param userName 用户名
	 * @throws DataAccessException
	 */
	public void deleteUserRole(String userName) throws DataAccessException;
	
	/**
	 * 删除用户和业务的关联关系
	 * @param userName 用户名
	 * @throws DataAccessException
	 */
	public void deleteUserBusiness(String userName) throws DataAccessException;
	
	/**
	 * 读取用户信息
	 * @param userName 用户名
	 * @return 用户
	 * @throws DataAccessException
	 */
	public User getUser(String userName) throws DataAccessException;
	
	/**
	 * 读取所有用户信息(通常供系统超级管理员使用)
	 * @return 用户列表
	 * @throws DataAccessException
	 */
	public List<User> getUserList() throws DataAccessException;
	
	/**
	 * 读取特定用户管理下的所有用户信息
	 * @param userName 用户名称
	 * @return 用户列表
	 * @throws DataAccessException
	 */
	public List<User> getUserList(String userName) throws DataAccessException;
	
	/**
	 * 分页读取所有用户信息(通常供系统超级管理员使用)
	 * @param start 开始记录
	 * @param limit 每页记录数
	 * @return PagingResultDTO 分页结果
	 * @throws DataAccessException
	 */
	public PagingResultDTO<User> getUserList(int start, int limit) throws DataAccessException;
	
	public List<Area> getUserAreaList(String userName)throws DataAccessException;
	/**
	 * 分页读取特定用户管理下的所有用户信息
	 * @param userName 用户名
	 * @param start 开始记录
	 * @param limit 每页记录数
	 * @return PagingResultDTO 分页结果
	 * @throws DataAccessException
	 */
	public PagingResultDTO<User> getUserList(String userName, int start, int limit) throws DataAccessException;
}
