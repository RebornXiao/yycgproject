package com.zzvcom.sysmag.service;

import java.util.List;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.SysWebserviceCfg;
import com.zzvcom.sysmag.pojo.WebserviceType;
import com.zzvcom.sysmag.pojo.SubSystem;

public interface SysWebserviceCfgService {
	public PagingResultDTO loadSysWsCfgList(String sysname,int start,int limit);
	public void deleteSysWsCfg(String ids);
	
	/**
	 * 增加配置
	 * @param sysid 子系统ID
	 * @param typeIds 以逗号隔开的多个服务类型ID
	 * @param port 端口号
	 * @return
	 */
	public SysWebserviceCfg getNameById(String wsId);
	public void insertSysWsCfg(String sysid,String typeIds,String port,String servicename, String serviceUrn,String namespace);
	public List<SubSystem> loadSystemList();
	public List<WebserviceType> loadWebServiceTypeList();
	public void update(String id,String sysid,String typeid,String port,String servicename,String serviceUrn,String namespace);
}
