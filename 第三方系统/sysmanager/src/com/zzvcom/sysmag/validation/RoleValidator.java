package com.zzvcom.sysmag.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.persistence.dao.RoleDao;
import com.zzvcom.sysmag.pojo.Role;

@Component
public class RoleValidator implements Validator {
	private Role vldtRole;
	private Role rsltRole;
	@Autowired RoleDao roleDao;
	public Errors validate(Object validateObj) {
		vldtRole = (Role)validateObj;
		Errors errors = new Errors();
		rsltRole = roleDao.getRoleByName(vldtRole.getRoleName());
		if (rsltRole != null && !rsltRole.getRoleId().equals(vldtRole.getRoleId())) {
			errors.reject("roleName", MessageInfo.EXSITS_ROLE_NAME);
		}
		return errors;
	}

}
