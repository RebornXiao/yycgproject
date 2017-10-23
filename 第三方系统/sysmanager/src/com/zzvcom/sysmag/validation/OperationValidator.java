package com.zzvcom.sysmag.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.persistence.dao.OperationDao;
import com.zzvcom.sysmag.pojo.Operation;

@Component
public class OperationValidator implements Validator {
	private Operation rsltOpt;
	private Operation vdltOpt;
	private boolean validateOptId;

	private Errors errors;

	@Autowired
	private OperationDao operationDao;

	public boolean isValidateOptId() {
		return validateOptId;
	}

	public void setValidateOptId(boolean validateOptId) {
		this.validateOptId = validateOptId;
	}

	public Errors validate(Object validateObj) {
		vdltOpt = (Operation) validateObj;
		errors = new Errors();
		if(validateOptId) {
			validateOptId();
		}
		validateOptName();
		return errors;
	}

	private void validateOptName() {
		List<Operation> optList = operationDao.getOperationByName(vdltOpt.getOperationName());
		if (!optList.isEmpty()) {
			for (Operation operation : optList) {
				rsltOpt = operation;
				if (!rsltOpt.equals(vdltOpt) && rsltOpt.inSameModuleWith(vdltOpt)) {
					errors.reject("operationName", MessageInfo.EXSITS_MODULENAME_IN_SAME_BOTTOM_MODULE);
				}
			}
		}

	}

	private void validateOptId() {
		rsltOpt = operationDao.getOperationById(vdltOpt.getOperationId());
		if (rsltOpt != null) {
			errors.reject("operationId", MessageInfo.EXSITS_OPERATIONID);
		}
	}

}
