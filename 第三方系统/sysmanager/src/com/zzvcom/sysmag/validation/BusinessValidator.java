package com.zzvcom.sysmag.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.persistence.dao.BusinessDao;
import com.zzvcom.sysmag.pojo.Business;
/**
 * 业务表单校验器
 * 负责业务添加与修改表单的服务器端验证
 * @author Wang Xiaoming
 */

@Component("businessValidator")
public class BusinessValidator implements Validator {

	@Autowired
	private BusinessDao businessDao;
	private Business vldtBuss;
	private Business rsltBuss;
	private Errors errors;

	public Errors validate(Object validateObj) {
		errors = new Errors();
		vldtBuss = (Business) validateObj;
		validateBusinessName();
		validateBusinessCode();
		return errors;
	}

	private void validateBusinessName() {
		rsltBuss = businessDao.getBusinessByName(vldtBuss.getBusinessName());
		if(rsltBuss != null) {
			if (!rsltBuss.equals(vldtBuss)) {
				errors.reject("businessName", MessageInfo.EXSITS_BUSINESSNAME);
			}
		}
	}

	private void validateBusinessCode() {
		rsltBuss = businessDao.getBusinessByCode(vldtBuss.getBusinessCode());
		if(rsltBuss != null) {
			if (!rsltBuss.equals(vldtBuss)) {
				errors.reject("businessCode", MessageInfo.EXSITS_BUSINESSCODE);
			}
		}
	}

}
