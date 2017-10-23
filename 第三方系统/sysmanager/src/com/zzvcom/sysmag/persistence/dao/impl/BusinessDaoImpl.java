package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.BusinessDao;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;

public class BusinessDaoImpl extends BaseDaoiBatis implements BusinessDao {

	public void deleteBusiness(String businessId) throws DataAccessException {
		getSqlMapClientTemplate().delete("Business.deleteBusiness", businessId);

	}

	public Business getBusinessById(String businessId)
			throws DataAccessException {
		Business business = (Business) getSqlMapClientTemplate()
				.queryForObject("Business.getBusinessById", businessId);
		return business;
	}

	@SuppressWarnings("unchecked")
	public List<Business> getAllBusinessList() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
		"Business.getAllBusinessList");
	}

	@SuppressWarnings("unchecked")
	public PagingResultDTO getBusinessList(int start, int limit)
			throws DataAccessException {
		return getPagingResultMap("Business.getBusinessList", null, start, limit);
	}

	public void insertBusiness(Business business) throws DataAccessException {
		getSqlMapClientTemplate().insert("Business.insertBusiness", business);

	}

	public void updateBusiness(Business business) throws DataAccessException {
		getSqlMapClientTemplate().insert("Business.updateBusiness", business);
	}

	public Business getBusinessByCode(String businessCode)
			throws DataAccessException {
		Business business = (Business) getSqlMapClientTemplate()
				.queryForObject("Business.getBusinessByCode", businessCode);
		return business;
	}

	public Business getBusinessByName(String businessName)
			throws DataAccessException {
		Business business = (Business) getSqlMapClientTemplate()
				.queryForObject("Business.getBusinessByName", businessName);
		return business;
	}

	@SuppressWarnings("unchecked")
	public List<Business> getUsingBusiness(String businessId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
				"Business.getUsedBusiness", businessId);
	}

	@SuppressWarnings("unchecked")
	public List<Business> getBusinessList(String userName)
			throws DataAccessException {
		List<Business> bizList = getSqlMapClientTemplate().queryForList(
				"Business.getBusinessListForCommonUser", userName);
		if (bizList == null || bizList.isEmpty()) {
			return bizList;
		} else {
			List<String> bizCodeList = new ArrayList<String>();
			for (Business business : bizList) {
				bizCodeList.add(business.getBusinessCode());
			}
			return getBusinessListByBizCodes(bizCodeList);
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Business> getBusinessListByBizCodes(List<String> bizCodeList)
			throws DataAccessException {
		if (bizCodeList.contains(Business.THE_ALL_BUSINESS_CODE)) {
			return getEntityBusinessList();
		} else {
			Map<String, List<String>> params = new HashMap<String, List<String>>();
			params.put("bizCodeList", bizCodeList);
			return getSqlMapClientTemplate().queryForList(
					"Business.getBusinessListByBizCodes", params);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Business> getEntityBusinessList() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
				"Business.getEntityBusinessList",
				Business.THE_ALL_BUSINESS_CODE);
	}

	public void saveOrder(Business business) {
		getSqlMapClientTemplate().update("Business.updateBusinessShowOder", business);
		
	}

	public List<Business> getDirectBusinessList(String userName)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
				"Business.getBusinessListForCommonUser", userName);
	}

}
