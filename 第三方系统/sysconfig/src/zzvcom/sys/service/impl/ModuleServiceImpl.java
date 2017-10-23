package zzvcom.sys.service.impl;

import java.util.Date;
import java.util.List;

import zzvcom.log.util.UUIDBuild;
import zzvcom.sys.dao.ModuleDao;
import zzvcom.sys.dao.ModuleOperDao;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.sys.service.ModuleService;
import zzvcom.sys.util.ReMsg;
import zzvcom.util.PageUtil;

public class ModuleServiceImpl implements ModuleService {

	private ModuleDao moduleDao;
	private ModuleOperDao operDao;
	private static final String TOP_MODULE_ID = "71";
	private static final String MODULE_BEGIN_SUFFIX = "71.";

	public List<VcomSysModule> getModuleList() {

		return moduleDao.getModuleList();
	}

	public ReMsg saveModule(VcomSysModule moduleObj) throws SaveOrUpdateException {
		if (moduleDao.isContainModulename(moduleObj.getParentid(), moduleObj
				.getModulename()))
			return new ReMsg(moduleObj.getModulename(), "已经存在，无需添加！");
		else {
			String moduleid = moduleObj.getParentid();
			List<VcomSysOperation> operList = operDao
					.getOperationListByModuleid(moduleid, null, null);
			if (null != operList && !operList.isEmpty())
				return new ReMsg(moduleObj.getModulename(), "下存在相应的操作，无法添加模块！");
		}

		// 设置时间
		Date now = new Date();
		moduleObj.setUpdatetime(now);
		moduleObj.setCreatetime(now);
		
		String parentid = moduleObj.getParentid();
		if(parentid.equals(TOP_MODULE_ID) || parentid.startsWith(MODULE_BEGIN_SUFFIX)) {
			moduleObj.setId(this.newModuleId(parentid));
		}
		else {
			moduleObj.setId(UUIDBuild.getUUID());
		}
		moduleDao.create(moduleObj);
		return new ReMsg(moduleObj.getModulename(), "新增成功！", true);
	}
	
	private String newModuleId(String parentId) {
		List<VcomSysModule> moduleList = moduleDao.getAllModuleListByParentid(parentId);
		int maxCode = 0;
		if (!moduleList.isEmpty()) {
			for (VcomSysModule module : moduleList) {
				String[] code = module.getId().split("[.]");
				int currCode = Integer.parseInt(code[code.length - 1]);
				if (currCode > maxCode) {
					maxCode = currCode;
				}
			}
		}

		maxCode++;
		String newCode = String.valueOf(maxCode);
		if(maxCode < 10) {
			newCode = "0" + maxCode;
		}
		if (parentId.equals("-1")) {
			return  TOP_MODULE_ID + "." + newCode;
		}
		return parentId + "." + newCode;
	}

	public ReMsg updateModule(VcomSysModule moduleObj) throws SaveOrUpdateException  {
		if (moduleDao.isContainModulename(moduleObj.getParentid(), moduleObj
				.getModulename(), moduleObj.getId()))
			return new ReMsg(moduleObj.getModulename(), "已经存在，无法修改！");
		else {
			moduleObj.setUpdatetime(new Date());// 设置修改时间
			moduleDao.saveOrupdate(moduleObj);
			return new ReMsg(moduleObj.getModulename(), "修改成功！", true);
		}

	}

	public ReMsg deleteModule(String id) throws ObjectNotFindException, DeleteException   {
		VcomSysModule moduleObj = moduleDao.query(id);
		if (null == moduleObj)
			return null;

		List<VcomSysModule> childList = moduleDao.getModuleListByParentid(id,
				null, null);
		//
		if (!childList.isEmpty())
			return new ReMsg(moduleObj.getModulename(), "下存在子模块，无法删除！");

		//
		List<VcomSysOperation> operList = operDao.getOperationListByModuleid(
				id, null, null);
		if (!operList.isEmpty())
			return new ReMsg(moduleObj.getModulename(), "下存在操作，无法删除！");

		// 删除
		moduleDao.delete(moduleObj);
		return new ReMsg(moduleObj.getModulename(), "删除成功！", true);

	}

	public ReMsg deleteOper(String id) throws ObjectNotFindException, DeleteException   {
		VcomSysOperation operObj = operDao.query(id);
		if (null == operObj)
			return null;
		// 删除
		operDao.delete(operObj);
		return new ReMsg(operObj.getOpername(), "删除成功！", true);

	}

	public List<VcomSysModule> getModuleListByParentid(String parentid,
			String modulename, PageUtil page) {

		return moduleDao.getModuleListByParentid(parentid, modulename, page);
	}

	public List<VcomSysOperation> getOperationList() {

		return operDao.getOperationList();
	}

	public VcomSysModule getModuleObjById(String id) throws ObjectNotFindException {

		return moduleDao.query(id);
	}

	public List<VcomSysModule> getModuleListByIds(String inIds, String notinIds) {

		return moduleDao.getModuleListByIds(inIds, notinIds);
	}

	public List<VcomSysOperation> getOperationListByIds(String inIds,
			String notinIds) {

		return operDao.getOperationListByIds(inIds, notinIds);
	}

	public List<VcomSysModule> getModuleListByIds(String ids, boolean isIn) {
		if (isIn)
			return moduleDao.getModuleListByIds(ids, null);
		else
			return moduleDao.getModuleListByIds(null, ids);
	}

	public List<VcomSysOperation> getOperationListByIds(String ids, boolean isIn) {
		if (isIn)
			return operDao.getOperationListByIds(ids, null);
		else
			return operDao.getOperationListByIds(null, ids);
	}

	public ModuleDao getModuleDao() {
		return moduleDao;
	}

	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	public List<VcomSysOperation> getOperationListByModuleid(String moduleid,
			String opername, PageUtil page) {

		return operDao.getOperationListByModuleid(moduleid, opername, page);
	}
	public List<VcomSysOperation> getOperationListByModuleid(String moduleid) {
		
		return operDao.getOperationListByModuleid(moduleid, null, null);
	}
	

	public VcomSysOperation getOperById(String id) throws ObjectNotFindException  {

		return operDao.query(id);
	}

	public ReMsg saveOper(VcomSysOperation oper) throws SaveOrUpdateException  {
		if (operDao.isContainOpername(oper.getModuleid(), oper.getOpername()))
			return new ReMsg(oper.getOpername(), "已经存在，无需添加！");
		else {
			// 设置时间
			Date now = new Date();
			oper.setUpdatetime(now);
			oper.setCreatetime(now);
			operDao.create(oper);
			return new ReMsg(oper.getOpername(), "新增成功！", true);
		}

	}

	public ReMsg updateOper(VcomSysOperation operObj) throws SaveOrUpdateException  {
		if (operDao.isContainOpername(operObj.getModuleid(), operObj.getOpername(),
				operObj.getId()))
			return new ReMsg(operObj.getOpername(), "已经存在，无法修改！");
		else {
			operObj.setUpdatetime(new Date());// 设置修改时间
			operDao.saveOrupdate(operObj);
			return new ReMsg(operObj.getOpername(), "修改成功！", true);
		}

	}

	public ModuleOperDao getOperDao() {
		return operDao;
	}

	public void setOperDao(ModuleOperDao operDao) {
		this.operDao = operDao;
	}

}
