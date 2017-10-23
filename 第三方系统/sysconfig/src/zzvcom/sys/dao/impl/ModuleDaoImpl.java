package zzvcom.sys.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import zzvcom.sys.dao.ModuleDao;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.util.PageUtil;

/**
 * 用户数据访问实现类
 * 
 * @author
 * 
 */

public class ModuleDaoImpl extends SqlMapClientDaoSupport implements ModuleDao {

	public static final Logger log = Logger.getLogger(ModuleDaoImpl.class);

	public List<VcomSysModule> getModuleList() {
		return this.getSqlMapClientTemplate().queryForList(
				"vcommodule.getModuleList");

	}

	public List<VcomSysModule> getModuleListByIds(String inIds, String notinIds) {
		if (null != inIds && !"".equals(inIds.trim()))
			return this.getSqlMapClientTemplate().queryForList(
					"vcommodule.getModuleListByIds", inIds);
		if (null != notinIds && !"".equals(notinIds.trim()))
			return this.getSqlMapClientTemplate().queryForList(
					"vcommodule.getModuleListByNotIds", notinIds);
		return null;
	}

	public List<VcomSysModule> getModuleListByParentid(String parentid,
			String modulename, PageUtil page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", parentid);
		map.put("modulename", StringEscapeUtils.escapeSql(modulename));

		if (null != page) {
			int count = (Integer) this.getSqlMapClientTemplate()
					.queryForObject("vcommodule.getModuleListCountByParentid",
							map);
			page.setTotalRow(count);

			map.put("start", page.getStart());
			map.put("end", page.getEnd());
			map.put("rowend", page.getRowend());
		}

		return this.getSqlMapClientTemplate().queryForList(
				"vcommodule.getModuleListByParentid", map);

	}

	public boolean isContainModulename(String parentid, String modulename,
			String exceptid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentid", parentid);
		map.put("modulename", modulename);
		map.put("exceptid", exceptid);

		int count = (Integer) this.getSqlMapClientTemplate().queryForObject(
				"vcommodule.isContainModulename", map);

		if (0 == count)
			return false;
		else
			return true;

	}

	public boolean isContainModulename(String parentid, String modulename) {
		return isContainModulename(parentid, modulename, "-1l");
	}

	public String create(VcomSysModule entity) throws SaveOrUpdateException {

		return (String) this.getSqlMapClientTemplate().insert(
				"vcommodule.create", entity);
	}

	public void delete(VcomSysModule entity) throws DeleteException {
		this.getSqlMapClientTemplate().delete("vcommodule.delete",
				entity.getId());
	}

	public VcomSysModule query(String id) throws ObjectNotFindException {
		return (VcomSysModule) this.getSqlMapClientTemplate().queryForObject(
				"vcommodule.query", id);
	}

	public void saveOrupdate(VcomSysModule entity) throws SaveOrUpdateException {
		this.getSqlMapClientTemplate().update("vcommodule.update", entity);

	}
	
	public List<VcomSysModule> getAllModuleListByParentid(String parentid) {
		return this.getSqlMapClientTemplate().queryForList("vcommodule.getAllModuleListByParentid", parentid);
	}

}
