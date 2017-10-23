package zzvcom.sys.service;

import java.util.List;

import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.sys.util.ReMsg;
import zzvcom.util.PageUtil;

public interface ModuleService {
	/**
	 * 根据parentid分页获取模块信息
	 * 
	 * @param parentid
	 *            父id
	 * @param modulename
	 *            模块（菜单）名称
	 * @param page
	 * @return
	 */
	public List<VcomSysModule> getModuleListByParentid(String parentid,
			String modulename, PageUtil page);

	/**
	 * 获得所有的ModuleList
	 * 
	 * @return
	 */
	public List<VcomSysModule> getModuleList();

	/**
	 * 根据id获得Module对象
	 * 
	 * @return
	 */
	public VcomSysModule getModuleObjById(String id)
			throws ObjectNotFindException;

	/**
	 * 删除模块
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ReMsg deleteModule(String id) throws ObjectNotFindException,
			DeleteException;

	/**
	 * 删除操作
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ReMsg deleteOper(String id) throws ObjectNotFindException,
			DeleteException;

	/**
	 * 保存模块
	 * 
	 * @param moduleObj
	 * @return
	 * @throws Exception
	 */
	public ReMsg saveModule(VcomSysModule moduleObj)
			throws SaveOrUpdateException;

	/**
	 * 修改模块
	 * 
	 * @param moduleObj
	 * @return
	 * @throws Exception
	 */
	public ReMsg updateModule(VcomSysModule moduleObj)
			throws SaveOrUpdateException;

	/**
	 * 获得所有的操作列表
	 * 
	 * @return
	 */
	public List<VcomSysOperation> getOperationList();

	/**
	 * 获取模块列表
	 * 
	 * @param inIds
	 *            包括的id字符串，如 "1,2,3"
	 * @param notinIds
	 *            不包括的id字符串，如 "1,2,3"
	 * @return
	 */
	public List<VcomSysModule> getModuleListByIds(String inIds, String notinIds);

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
	 * 获取操作对象
	 * 
	 * @param id
	 * 
	 * 
	 * @return
	 */
	public VcomSysOperation getOperById(String id) throws ObjectNotFindException;

	/**
	 * 保存操作
	 * 
	 * @param oper
	 * @return
	 * @throws Exception
	 */
	public ReMsg saveOper(VcomSysOperation oper) throws SaveOrUpdateException;

	/**
	 * 修改操作
	 * 
	 * @param operObj
	 * @return
	 * @throws Exception
	 */
	public ReMsg updateOper(VcomSysOperation operObj)
			throws SaveOrUpdateException;

	/**
	 * 获取模块列表
	 * 
	 * @param ids
	 *            id字符串，如 "1,2,3"
	 * @param isIn
	 *            true 表示 in ，false表示not in
	 * @return
	 */
	public List<VcomSysModule> getModuleListByIds(String ids, boolean isIn);

	/**
	 * 获取操作列表
	 * 
	 * @param ids
	 *            id字符串，如 "1,2,3"
	 * @param isIn
	 *            true 表示 in ，false表示not in
	 * @return
	 */
	public List<VcomSysOperation> getOperationListByIds(String ids, boolean isIn);

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
	 * 根据moduleid获取操作列表
	 * 
	 * @param moduleid
	 
	 * @return
	 */
	public List<VcomSysOperation> getOperationListByModuleid(String moduleid);
}
