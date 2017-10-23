package com.zzvcom.sysmag.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.persistence.dao.UserDao;
import com.zzvcom.sysmag.pojo.User;

@Component
public class UserValidator implements Validator {
	private User vdltUser;
	private User rsltUser;
	private boolean validSwitch = true;
	@Autowired
	private UserDao userDao;

	public Errors validate(Object validateObj) {
		vdltUser = (User) validateObj;
		Errors errors = new Errors();
		/*如果校验打开*/
		if (validSwitch == true) {
			if (vdltUser.isAdmin()) {
				errors.reject("userName", MessageInfo.EXSITS_USER_NAME);
				return errors;
			}
			rsltUser = userDao.getUser(vdltUser.getUserName());
			/*如果存在相同用户*/
			if (rsltUser != null) {
				errors.reject("userName", MessageInfo.EXSITS_USER_NAME);
			}
		}

		return errors;
	}

	public void setValidSwitch(boolean validSwitch) {
		this.validSwitch = validSwitch;
	}

}
