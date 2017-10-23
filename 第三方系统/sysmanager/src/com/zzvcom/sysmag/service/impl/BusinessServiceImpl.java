package com.zzvcom.sysmag.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.persistence.dao.BusinessDao;
import com.zzvcom.sysmag.persistence.dao.TaskRecorder;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Task;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.BusinessService;

/**
 * 业务管理Service实现类
 * 
 * @author Wang Xiaoming
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private TaskRecorder taskRecorder;

	@Transactional
	public void removeBusiness(User loginUser, String businessId) {
		Business business = businessDao.getBusinessById(businessId);
		if (this.isUsed(businessId)) {
			throw new IllegalDeleteException(new Business(businessId));
		}
		businessDao.deleteBusiness(businessId);
		taskRecorder.record(loginUser, business, Task.OPT_TYPE_DEL);
	}

	public Business loadBusiness(String businessId) {
		return businessDao.getBusinessById(businessId);
	}

	public List<Business> loadBusinessList() {
		return businessDao.getAllBusinessList();
	}

	@Transactional(readOnly = true)
	public PagingResultDTO loadBusinessList(int start, int limit) {
		return businessDao.getBusinessList(start, limit);
	}

	@Transactional
	public void saveBusiness(User loginUser, Business business) {
		if (business.getBusinessId().equals("")) {
			businessDao.insertBusiness(business);
			// 记录数据同步任务
			taskRecorder.record(loginUser, businessDao
					.getBusinessByName(business.getBusinessName()),
					Task.OPT_TYPE_ADD);
		} else {
			businessDao.updateBusiness(business);
			// 记录数据同步任务
			taskRecorder.record(loginUser, business, Task.OPT_TYPE_EDIT);
		}

	}

	private boolean isUsed(String businessId) {
		if (businessDao.getUsingBusiness(businessId).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Business loadBusinessByCode(String bizCode) {
		return businessDao.getBusinessByCode(bizCode);
	}

	public List<Business> loadBusinessListByCodes(List<String> bizCodes) {
		return businessDao.getBusinessListByBizCodes(bizCodes);
	}

	public Business getBusinessByName(String businessName) {
		return businessDao.getBusinessByName(businessName);
	}

	public List<Business> getEntityBusinessList() {
		return businessDao.getEntityBusinessList();
	}

	public void saveOrder(String itemIds) {
		String[] items = itemIds.split(",");
		for (int i = 0; i < items.length; i++) {
			Business business = new Business();
			business.setBusinessCode(items[i]);
			business.setShowOrder(i + 1);
			businessDao.saveOrder(business);
		}

	}
}
