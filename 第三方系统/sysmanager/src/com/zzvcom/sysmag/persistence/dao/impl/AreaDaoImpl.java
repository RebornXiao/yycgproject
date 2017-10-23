package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.AreaDao;
import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
public class AreaDaoImpl extends BaseDaoiBatis implements AreaDao {

	public void deleteArea(String areaId) throws DataAccessException {
		getSqlMapClientTemplate().delete("Area.deleteArea", areaId);
	}

	public void insertArea(Area area) throws DataAccessException {
		getSqlMapClientTemplate().insert("Area.insertArea", area);

	}

	public void updateArea(Area area) throws DataAccessException {
		getSqlMapClientTemplate().update("Area.updateArea", area);

	}

	public PagingResultDTO getAreaList(int start, int limit, String areaId)
			throws DataAccessException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaId);
		return getPagingResultMap("Area.getAreaListForAdminUser", params, start, limit);
	}

	public Integer getMaxAreaNumber(String parentId) throws DataAccessException {
		return (Integer)getSqlMapClientTemplate().queryForObject("Area.getMaxAreaId", parentId);
	}

	public PagingResultDTO getAreaList(int start, int limit, String areaId,	String userName) throws DataAccessException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("areaId", areaId);
		params.put("userName", userName);
		return getPagingResultMap("Area.getAreaListForCommonUser", params, start, limit);
	}

	public List<Area> getLowerAreaList(String areaId) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getLowerAreaList", areaId);
	}
	
	public List<Area> getNeInfoAreaList(String areaId) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getNeInfoAreaList", areaId);
	}

	public List<Area> getUserDirectAreaList() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getAdminUserDirectAreaList");
	}

	public List<Area> getUserDirectAreaList(String userName) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getCommonUserDirectAreaList", userName);
	}

	public Area getAreaById(String areaId) throws DataAccessException {
		return (Area)getSqlMapClientTemplate().queryForObject("Area.getAreaById", areaId);
	}

	public Area getAreaByName(String areaName) throws DataAccessException {
		return (Area)getSqlMapClientTemplate().queryForObject("Area.getAreaByName", areaName);
	}

	public List<Area> getRoleUsedAreaList(String areaId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getRoleUsedAreaList", areaId);
	}

	public List<Area> getUserUsedAreaList(String areaId)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getUserUsedAreaList", areaId);
	}
	
	public List<Area> getLearningcentersByareaid(String areaId) throws DataAccessException{
		return getSqlMapClientTemplate().queryForList("Area.getLearningcentersByareaid", areaId);
		
	}
	public List<Area> getAllLowerAreaList(String areaId) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("Area.getAllLowerAreaList", areaId);
	}

	public Area getRoleArea(String roleId) throws DataAccessException {
		return (Area)getSqlMapClientTemplate().queryForObject("Area.getRoleArea", roleId);
	}

	public List<String> getUpperAreaIdListByAreaIds(List<String> areaIds)
			throws DataAccessException {
		Map params = new HashMap();
		params.put("areaIdList", areaIds);
		return getSqlMapClientTemplate().queryForList("Area.getUpperAreaIdListByAreaIds", params);
	}

	public Area getAreaByTeleComCode(String teleComCode) throws DataAccessException {
		return (Area) getSqlMapClientTemplate().queryForObject("Area.getAreaByTeleComCode", teleComCode);
	}


}
