package com.zzvcom.sysmag.persistence.dao;

import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.SysWebserviceCfg;
import com.zzvcom.sysmag.pojo.WebserviceType;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface SysWebserviceCfgDao {
	public PagingResultDTO getSysWsCfgList(String sysname, int start, int limit)
			throws DataAccessException;

	public List<SubSystem> getSystemList() throws DataAccessException;

	public List<WebserviceType> getWebServiceList() throws DataAccessException;

	public void delete(String id) throws DataAccessException;

	public void add(Map<String, String> param) throws DataAccessException;
	
	public int getSameCfg(Map<String, String> param) throws DataAccessException;
	
	public void update(Map<String,String> param) throws DataAccessException;
	public  SysWebserviceCfg getIdByName(String wsname);
	public  SysWebserviceCfg getNameById(String wsId);
}
