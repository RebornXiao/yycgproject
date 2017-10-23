package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.SubSystem;

/**
 * 子系统数据访问对象接口
 * @author Wang Xiaoming
 */
public interface SubSystemDao {
	/**
	 * 根据子系统Id读取子系统信息
	 * @param systemId 子系统Id
	 * @return 子系统对象
	 * @throws DataAccessException
	 */
	public SubSystem getSystemById(String systemId) throws DataAccessException;
	
	/**
	 * 根据子系统名称读取子系统信息
	 * @param systemName 子系统名称
	 * @return 子系统对象
	 * @throws DataAccessException
	 */
	public SubSystem getSystemByName(String systemName) throws DataAccessException;
	
	/**
	 * 读取全部子系统列表(通常供系统超级管理员使用)
	 * @return 子系统列表
	 * @throws DataAccessException
	 */
	public List<SubSystem> getSystemList() throws DataAccessException;
	
	/**
	 * 读取<b>特定角色</b>的子系统列表
	 * @param roleId 角色Id
	 * @return 子系统列表
	 * @throws DataAccessException
	 */
	public List<SubSystem> getSystemList(String roleId) throws DataAccessException;
	
	/**
	 * 读取特定部署节点所属子系统信息
	 * @param deployNodeId 部署节点Id
	 * @return 子系统对象
	 * @throws DataAccessException
	 */
	public SubSystem getSystemByDeployNodeId(String deployNodeId) throws DataAccessException;
	
	public void insertSystem(SubSystem system) throws DataAccessException;
}
