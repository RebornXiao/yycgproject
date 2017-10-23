package com.zzvcom.sysmag.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.persistence.dao.SysWebserviceCfgDao;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.SysWebserviceCfg;
import com.zzvcom.sysmag.pojo.WebserviceType;
import com.zzvcom.sysmag.service.SysWebserviceCfgService;

@Service("sysWebserviceCfgService")
public class SysWebserviceCfgServiceImpl implements SysWebserviceCfgService {
	@Autowired
	private SysWebserviceCfgDao sysWebserviceCfgDao;
	
	public PagingResultDTO loadSysWsCfgList(String sysname, int start, int limit) {
		return sysWebserviceCfgDao.getSysWsCfgList(sysname, start, limit);
	}
	
	@Transactional
	public void deleteSysWsCfg(String ids) {
		String[] id = ids.split(",");
		for(String temp:id){
			sysWebserviceCfgDao.delete(temp);
		}
	}

	public List<SubSystem> loadSystemList() {
		return sysWebserviceCfgDao.getSystemList();
	}

	public List<WebserviceType> loadWebServiceTypeList() {
		return sysWebserviceCfgDao.getWebServiceList();
	}
	
	@Transactional
	public void insertSysWsCfg(String sysid, String typeIds, String port,String servicename, String serviceUrn,String namespace){
		String[] typeid = typeIds.split(",");
		
		Map<String,String> param;
		
		for(String temp:typeid){
			param = new HashMap<String,String>();
			param.put("sysid", sysid);
			param.put("typeid", temp);
			param.put("port", port);
			param.put("servicename", servicename);
			param.put("serviceUrn", serviceUrn);
			param.put("namespace", namespace);
			if (!isCfgExist(param)) {
				sysWebserviceCfgDao.add(param);
			}
		}
	}
	
	private boolean isCfgExist(Map<String,String> param) {
		return sysWebserviceCfgDao.getSameCfg(param) > 0;
	}

	public void update(String id,String sysid, String typeid, String port,
			String servicename,String serviceUrn,String namespace) {
			Map<String,String> param =  new HashMap<String,String>();
			param.put("id", id);
			param.put("sysid", sysid);
			param.put("typeid", typeid.replace(",", ""));
			param.put("port", port);
			param.put("servicename", servicename);
			param.put("serviceUrn", serviceUrn);
			param.put("namespace", namespace);
			sysWebserviceCfgDao.update(param);
	}

	public SysWebserviceCfg getNameById(String wsId) {
		// TODO Auto-generated method stub
		return sysWebserviceCfgDao.getNameById(wsId);
	}

}
