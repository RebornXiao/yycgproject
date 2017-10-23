package com.zzvcom.sysmag.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zzvcom.sysmag.persistence.BaseDaoiBatis;
import com.zzvcom.sysmag.persistence.dao.SysWebserviceCfgDao;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.SysWebserviceCfg;
import com.zzvcom.sysmag.pojo.WebserviceType;

public  class SysWebserviceCfgDaoImpl extends BaseDaoiBatis implements
		SysWebserviceCfgDao {

	public PagingResultDTO getSysWsCfgList(String sysname, int start, int limit) throws DataAccessException{
		return getPagingResultMap("SysWebserviceCfg.getSysWebserviceCfgList", sysname, start, limit);
	}

	public void delete(String id) throws DataAccessException{
		getSqlMapClientTemplate().delete("SysWebserviceCfg.deleteSysWebServiceCfgByID", id);
	}

	public List<SubSystem> getSystemList() throws DataAccessException{
		return getSqlMapClientTemplate().queryForList("SysWebserviceCfg.getSystemList");
	}

	public List<WebserviceType> getWebServiceList() throws DataAccessException{
		return getSqlMapClientTemplate().queryForList("SysWebserviceCfg.getWebserviceTypeList");
	}

	public void add(Map<String, String> param) throws DataAccessException{
		getSqlMapClientTemplate().update("SysWebserviceCfg.insertSysWebserviceCfg", param);
	}

	public int getSameCfg(Map<String, String> param)
			throws DataAccessException {
		return (Integer)getSqlMapClientTemplate().queryForObject("SysWebserviceCfg.isExists",param);
	}

	public void update(Map<String, String> param) throws DataAccessException {
		
		getSqlMapClientTemplate().update("SysWebserviceCfg.updateSysWebserviceCfg", param);
	}

	public SysWebserviceCfg getIdByName(String wsname) {
		// TODO Auto-generated method stub
		return  (SysWebserviceCfg) getSqlMapClientTemplate().queryForObject("SysWebserviceCfg.selectIdByName",wsname);
	}

	public SysWebserviceCfg getNameById(String wsId) {
		// TODO Auto-generated method stub
		return  (SysWebserviceCfg) getSqlMapClientTemplate().queryForObject("SysWebserviceCfg.selectNameById",wsId);
	}
}
