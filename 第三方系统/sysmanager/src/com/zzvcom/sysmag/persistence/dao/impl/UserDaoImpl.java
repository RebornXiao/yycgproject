package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.UserDao;
import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.User;
@SuppressWarnings("unchecked")
public class UserDaoImpl extends BaseDaoiBatis implements UserDao {

	public void deleteUser(String userName) throws DataAccessException {
		getSqlMapClientTemplate().delete("User.deleteUser", userName);

	}

	public void deleteUserArea(String userName)
			throws DataAccessException {
		getSqlMapClientTemplate().delete("User.deleteUserArea", userName);
	}

	public void deleteUserBusiness(String userName)
			throws DataAccessException {
		getSqlMapClientTemplate().delete("User.deleteUserBusiness", userName);
	}

	public void deleteUserRole(String userName)
			throws DataAccessException {
		getSqlMapClientTemplate().delete("User.deleteUserRole", userName);
	}

	public User getUser(String userName) throws DataAccessException {
		return (User)getSqlMapClientTemplate().queryForObject("User.getUser", userName);
	}

	public List<User> getUserList() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("User.getUserListForAdminUser");
	}

	public List<User> getUserList(String userName) throws DataAccessException {
		Map params = new HashMap();
		
		List<String> areaList = getSqlMapClientTemplate().queryForList("Role.getUserAreaList", userName);
		params.put("userName", userName);
		params.put("areaList", areaList);
		return getSqlMapClientTemplate().queryForList("User.getUserListForCommonUser", params);
	}
	public List<Area> getUserAreaList(String userName)throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getUserArea", userName);
	}

	public PagingResultDTO<User> getUserList(int start, int limit)
			throws DataAccessException {
		return getPagingResultMap("User.getUserListForAdminUser", null, start, limit);
	}

	public PagingResultDTO<User> getUserList(String userName, int start, int limit)
			throws DataAccessException {
		Map params = new HashMap();
		List<String> areaList = getSqlMapClientTemplate().queryForList("Role.getUserAreaList", userName);
		params.put("userName", userName);
		params.put("areaList", areaList);
		return getPagingResultMap("User.getUserListForCommonUser", params, start, limit);
	}

	public void insertUser(User user) throws DataAccessException {
		getSqlMapClientTemplate().insert("User.insertUser", user);
	}

	public void saveUserArea(String userName, String areaId)
			throws DataAccessException {
		Map params = new HashMap();
		params.put("userName", userName);
		params.put("areaId", areaId);
		getSqlMapClientTemplate().insert("User.insertUserArea", params);
	}

	public void saveUserBusiness(String userName, String businessId)
			throws DataAccessException {
		Map params = new HashMap();
		params.put("userName", userName);
		params.put("businessId", businessId);
		getSqlMapClientTemplate().insert("User.insertUserBusiness", params);
	}

	public void saveUserRole(String userName, String roleId)
			throws DataAccessException {
		Map params = new HashMap();
		params.put("userName", userName);
		params.put("roleId", roleId);
		getSqlMapClientTemplate().insert("User.insertUserRole", params);
	}

	public void updateUser(User user) throws DataAccessException {
		getSqlMapClientTemplate().update("User.updateUser", user);

	}

}
