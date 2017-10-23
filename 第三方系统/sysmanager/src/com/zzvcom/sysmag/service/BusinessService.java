package com.zzvcom.sysmag.service;

import java.util.List;

import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.User;

/**
 * 业务管理Service接口
 * @author Wang Xiaoming
 */
public interface BusinessService {
	/**
	 * 读取业务列表
	 * @return 业务列表
	 */
	public List<Business> loadBusinessList();
	
	/**
	 * 分页读取业务列表
	 * @param start 开始记录
	 * @param limit 每次读取记录数
	 * @return 分页业务列表
	 */
	public PagingResultDTO<Business> loadBusinessList(int start, int limit);
	
	/**
	 * 读取业务
	 * @param businessId 业务Id
	 * @return 业务
	 */
	public Business loadBusiness(String businessId);
	
	/**
	 * 保存业务
	 * @param business 业务
	 */
	public void saveBusiness(User loginUser, Business business);
	
	
	/**
	 * 移除业务
	 * @param businessId 业务Id
	 */
	public void removeBusiness(User loginUser, String businessId);
	
}
