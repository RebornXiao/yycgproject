package com.zzvcom.sysmag.persistence.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;

/**
 * 业务数据访问对象接口
 * 
 * @author Wang Xiaoming
 */

public interface BusinessDao {
	public void insertBusiness(Business business) throws DataAccessException;

	public void updateBusiness(Business business) throws DataAccessException;

	public void deleteBusiness(String businessId) throws DataAccessException;

	public Business getBusinessById(String businessId)
			throws DataAccessException;

	public Business getBusinessByName(String businessName)
			throws DataAccessException;

	public Business getBusinessByCode(String businessCode)
			throws DataAccessException;

	/**
	 * 读取所有业务列表(通常供<b>系统超级管理员</b>使用)<br>
	 * 包括"全部业务"
	 * 
	 * @return 业务列表
	 * @throws DataAccessException
	 */
	public List<Business> getAllBusinessList() throws DataAccessException;

	/**
	 * 读取所有全部实体业务<br>
	 * 不包括"全部业务"
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Business> getEntityBusinessList() throws DataAccessException;

	public List<Business> getBusinessListByBizCodes(List<String> bizCodeList)
			throws DataAccessException;

	/**
	 * 读取登录用户的直接对应业务(若对应业务为"全部业务",就单读取"全部业务",不再读取其他的业务)
	 * 
	 * @param userName
	 * @return
	 * @throws DataAccessException
	 */
	public List<Business> getDirectBusinessList(String userName)
			throws DataAccessException;

	/**
	 * 读取特定用户的业务列表
	 * 
	 * @param userName
	 *            用户名
	 * @return 业务列表
	 * @throws DataAccessException
	 */
	public List<Business> getBusinessList(String userName)
			throws DataAccessException;

	public PagingResultDTO<Business> getBusinessList(int start, int limit)
			throws DataAccessException;

	public List<Business> getUsingBusiness(String businessId)
			throws DataAccessException;

	public void saveOrder(Business business);
}
