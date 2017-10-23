package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.HashMap;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.UserLogDao;



public class UserLogDaoImpl	extends BaseDaoiBatis implements UserLogDao{
	
		public void addUserLog(HashMap map)throws DataAccessException{
			getSqlMapClientTemplate().insert("userLog.insertLog",map);
		}
	}


