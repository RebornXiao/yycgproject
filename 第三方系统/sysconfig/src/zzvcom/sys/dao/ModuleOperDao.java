package zzvcom.sys.dao;

import java.util.List;

import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.util.PageUtil;

/**
 * 用户接口
 * 
 * @author LHQ.
 * 
 */
public interface ModuleOperDao extends GenericDao<VcomSysOperation> {

	/**
	 * 在moduleid下是否包含相同的操作名称
	 * 
	 * @param moduleid
	 * @param opername
	 * @param exceptid
	 * @return
	 */
	public boolean isContainOpername(String moduleid, String opername,
			String exceptid);

	/**
	 * 在moduleid下是否包含相同的操作名称
	 * 
	 * @param moduleid
	 * @param modulename
	 * 
	 * @return
	 */
	public boolean isContainOpername(String moduleid, String opername);

 

	

	/**
	 * 获取操作列表
	 * 
	 * @param inIds
	 *            包括的id字符串，如 "1,2,3"
	 * @param notinIds
	 *            不包括的id字符串，如 "1,2,3"
	 * @return
	 */
	public List<VcomSysOperation> getOperationListByIds(String inIds,
			String notinIds);

	/**
	 * 根据moduleid获取操作列表
	 * 
	 * @param moduleid
	 * @param opername
	 * @param page
	 * @return
	 */
	public List<VcomSysOperation> getOperationListByModuleid(String moduleid,
			String opername, PageUtil page);

	 

	/**
	 * 获得所有的操作列表
	 * 
	 * @return
	 */
	public List<VcomSysOperation> getOperationList();
}
