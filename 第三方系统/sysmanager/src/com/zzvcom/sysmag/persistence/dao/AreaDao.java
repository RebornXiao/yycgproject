package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.PagingResultDTO;

/**
 * 区域数据访问对象接口
 * @author Wang Xiaoming
 */
public interface AreaDao {
	/**
	 * 分页读取特定区域下的所有区域信息列表(通常供<font color="red">系统超级管理员</font>使用)
	 * @param start 开始记录
	 * @param limit 每页记录数
	 * @param areaId 区域Id
	 * @return 区域列表
	 * @throws DataAccessException
	 */
	public PagingResultDTO getAreaList(int start, int limit, String areaId) throws DataAccessException;
	
	/**
	 * 分页读取<font color="red">特定用户</font>的特定区域下的区域信息列表
	 * @param start 开始记录
	 * @param limit 每页记录数
	 * @param areaId 区域Id
	 * @param userName 用户名
	 * @return 区域列表
	 * @throws DataAccessException
	 */
	public PagingResultDTO getAreaList(int start, int limit, String areaId, String userName) throws DataAccessException;
	
	/**
	 * 读取用户的直接关联区域(通常供系统超级管理员使用)
	 * 即级别最高的关联区域
	 * @return 区域列表
	 * @throws DataAccessException
	 */
	public List<Area> getUserDirectAreaList() throws DataAccessException;
	
	/**
	 * 读取特定用户的直接关联区域
	 * 即用户关联区域表中的区域记录
	 * @param userName 用户名
	 * @return 区域列表
	 * @throws DataAccessException
	 */
	public List<Area> getUserDirectAreaList(String userName) throws DataAccessException;
	
	/**
	 * 读取特定区域的<b>下一级</b>区域列表
	 * @param areaId 区域Id
	 * @return 下一级区域列表
	 * @throws DataAccessException
	 */
	public List<Area> getLowerAreaList(String areaId) throws DataAccessException;
	
	
	/**
	 * 读取特定区域下设备列表
	 * @param areaId 区域Id
	 * @return 设备
	 * @throws DataAccessException
	 */
	public List<Area> getNeInfoAreaList(String areaId) throws DataAccessException;
	
	/**
	 * 读取特定区域下的<b>所有下级区域</b>
	 * @param areaId 区域Id
	 * @return 所有下级区域列表
	 * @throws DataAccessException
	 */
	public List<Area> getAllLowerAreaList(String areaId) throws DataAccessException;
	
	/**
	 * 根据区域Id读取该区域信息
	 * @param areaId 区域Id
	 * @return 区域
	 * @throws DataAccessException
	 */
	public Area getAreaById(String areaId) throws DataAccessException;
	
	/**
	 * 根据区域名称读取该区域信息
	 * @param areaName 区域名称
	 * @return 区域
	 * @throws DataAccessException
	 */
	public Area getAreaByName(String areaName) throws DataAccessException;
	
	public Area getAreaByTeleComCode(String teleComCode) throws DataAccessException;
	
	public void insertArea(Area area) throws DataAccessException;
	public void updateArea(Area area) throws DataAccessException;
	public void deleteArea(String areaId) throws DataAccessException;
	
	/**
	 * 读取特定角色的区域信息
	 * @param roleId 角色Id
	 * @return 区域
	 * @throws DataAccessException
	 */
	public Area getRoleArea(String roleId) throws DataAccessException;
	
	/**
	 * 读取被用户所使用的区域列表
	 * @param areaId 区域Id
	 * @return 区域列表
	 * @throws DataAccessException
	 */
	public List<Area> getUserUsedAreaList(String areaId) throws DataAccessException;
	
	/**
	 * 读取该区域下的服务中心
	 * @param areaId 区域Id
	 * @return 区域列表
	 * @throws DataAccessException
	 */
	public List<Area> getLearningcentersByareaid(String areaId) throws DataAccessException;
	
	/**
	 * 读取被角色所使用的区域列表
	 * @param areaId 区域Id
	 * @return 区域列表
	 * @throws DataAccessException
	 */
	public List<Area> getRoleUsedAreaList(String areaId) throws DataAccessException;
	
	/**
	 * 获取某一区域下的最大区域Id号码
	 * @param parentId 区域Id
	 * @return 最大区域Id号码
	 * @throws DataAccessException
	 */
	public Integer getMaxAreaNumber(String areaId) throws DataAccessException;
	
	/**
	 * 获得一组区域的上级区域Id集合
	 * @param areaIds 区域id组
	 * @return 上级区域Id集合
	 * @throws DataAccessException
	 */
	public List<String> getUpperAreaIdListByAreaIds(List<String> areaIds) throws DataAccessException; 
	
}
