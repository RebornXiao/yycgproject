package com.zzvcom.sysmag.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.persistence.dao.ModuleDao;
import com.zzvcom.sysmag.pojo.Module;

/**
 * 模块校验器
 * @author Wang Xiaoming
 */
@Component
public class ModuleValidator implements Validator {
	private Module vdltModule;
	private Module rsltModule;
	private Errors errors;
	private boolean validModuleId;
	
	@Autowired
	private ModuleDao moduleDao;
	
	public Errors validate(Object validateObj) {
		errors = new Errors();
		vdltModule = (Module)validateObj;
		if (validModuleId) {  //判断是否需要校验ModuleId
			validateModuleId();
		}
		validateModuleName();
		return errors;
	}
	
	private void validateModuleName() {
		List<Module> sameNameModuleList = moduleDao.getModuleByName(vdltModule.getModuleName());
		if (!sameNameModuleList.isEmpty()) {
			/*判断模块类型*/
			if (vdltModule.getUpperModuleId().equals(Module.ROOT_MODULE_ID)) { //如果为一级模块
				for (Module topModule : sameNameModuleList) {
					rsltModule = topModule;
					/*判断在所属子系统中是否有同名模块*/
					if(!vdltModule.equals(rsltModule) && vdltModule.inSameSystemWith(rsltModule)) {
						errors.reject("moduleName", MessageInfo.EXSITS_MODULENAME_IN_SAME_SYSTEM);
					}
				}
			} else { //如果为二级模块
				for (Module bottomModule : sameNameModuleList) {
					rsltModule = bottomModule;
					/*判断在所属上级模块中是否有同名模块*/
					if(!vdltModule.equals(rsltModule) && vdltModule.inSameTopModuleWith(rsltModule)) {
						errors.reject("moduleName", MessageInfo.EXSITS_MODULENAME_IN_SAME_TOP_MODULE);
					}
				}
			}
		}
	}
	
	private void validateModuleId() {
		rsltModule = moduleDao.getModuleById(vdltModule.getModuleId());
		if(rsltModule != null) {
			errors.reject("moduleId", MessageInfo.EXSITS_MODULEID);
		}
	}

	public boolean isValidModuleId() {
		return validModuleId;
	}

	public void setValidModuleId(boolean validModuleId) {
		this.validModuleId = validModuleId;
	}

	
}
