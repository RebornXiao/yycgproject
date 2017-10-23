package com.zzvcom.sysmag.service;

import java.util.List;

import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.User;

/**
 * 区域管理Service接口
 * @author Wang Xiaoming
 */
public interface AreaService {
	
	/**
	 * 根据区域Id加载区域信息
	 * @param areaId 区域Id
	 * @return 区域
	 */
	public Area loadArea(String areaId);
	
	/**
	 * 加载特定用户的特定区域的<b>下一级</b>区域列表
	 * @param areaId 区域Id
	 * @param user 用户名
	 * @return 区域列表
	 */
	public List<Area> loadAreaList(String areaId, User user);
	
	/**
	 * 分页加载特定用户的特定区域下的所有下级区域列表
	 * @param start
	 * @param limit
	 * @param areaId
	 * @param user
	 * @return
	 */
	public PagingResultDTO loadAreaList(int start, int limit, String areaId, User user);
	
	public void saveArea(User loginUser,Area area);
	
	public void removeArea(User loginUser,String areaId);
	
	public boolean inSameUpperArea(List areaList);
	public Area  getAreaById(String areaId);
}
