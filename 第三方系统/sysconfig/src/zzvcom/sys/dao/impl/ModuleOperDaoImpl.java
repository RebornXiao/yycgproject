package zzvcom.sys.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import zzvcom.sys.dao.ModuleOperDao;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.util.PageUtil;

/**
 * 用户数据访问实现类
 * 
 * @author
 * 
 */

public class ModuleOperDaoImpl extends SqlMapClientDaoSupport implements
		ModuleOperDao {

	public static final Logger log = Logger.getLogger(ModuleOperDaoImpl.class);

	public List<VcomSysOperation> getOperationListByModuleid(String moduleid,
			String opername, PageUtil page) {
		//	 
		Map map = new HashMap();
		map.put("moduleid", moduleid);
		map.put("opername", StringEscapeUtils.escapeSql(opername));
		if (null != page) {
			int count = (Integer) this.getSqlMapClientTemplate()
					.queryForObject(
							"vcomoper.getOperationListCountByModuleid", map);
			page.setTotalRow(count);

			map.put("start", page.getStart());
			map.put("end", page.getEnd());
			map.put("rowend", page.getRowend());
		}
		return this.getSqlMapClientTemplate().queryForList(
				"vcomoper.getOperationListByModuleid", map);

	}

	public List<VcomSysOperation> getOperationList() {
		// String hql = "from VcomSysOperation o order by o.sort asc";
		// Query query = getSession().createQuery(hql);
		//
		// return query.list();
		return this.getSqlMapClientTemplate().queryForList(
				"vcomoper.getOperationListByModuleid");
	}

	private Long[] convertString2Long(String ids) {

		Long[] lids = new Long[ids.split(",").length];
		int i = 0;
		for (String s : ids.split(",")) {
			lids[i] = Long.parseLong(s);
			i++;
		}

		return lids;
	}

	public List<VcomSysOperation> getOperationListByIds(String inIds,
			String notinIds) {
		// Session session = this.getSession();
		// Criteria cri = session.createCriteria(VcomSysOperation.class);
		// if (null != inIds && !"".equals(inIds.trim()))
		// cri.add(Restrictions.in("id", this.convertString2Long(inIds)));
		// if (null != notinIds && !"".equals(notinIds.trim()))
		// cri.add(Restrictions.not(Restrictions.in("id", this
		// .convertString2Long(notinIds))));
		//
		// cri.addOrder(Order.asc("sort"));
		// return cri.list();

		Map<String, String> map = new HashMap<String, String>();

		if (null != inIds && !"".equals(inIds.trim()))
			map.put("inIds", inIds);
		if (null != notinIds && !"".equals(notinIds.trim()))
			map.put("notinIds", notinIds);
		
		
		return this.getSqlMapClientTemplate().queryForList(
				"vcomoper.getOperationListByIds", map);
	}

	public boolean isContainOpername(String moduleid, String opername,
			String exceptid) {
		// Session session = this.getSession();
		// Criteria cri = session.createCriteria(VcomSysOperation.class);
		// cri.add(Restrictions.eq("moduleid", moduleid));
		// cri.add(Restrictions.eq("opername", opername));
		//		
		// if (exceptid > 0)
		// cri.add(Restrictions.not(Restrictions.eq("id", exceptid)));
		//
		// if (null == cri.list() || cri.list().isEmpty())
		// return false;
		// else
		// return true;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("moduleid", moduleid);
		map.put("opername", opername);
		map.put("exceptid", exceptid);

		int count = (Integer) this.getSqlMapClientTemplate().queryForObject(
				"vcomoper.isContainOpername", map);

		if (0 == count)
			return false;
		else
			return true;
	}

	public boolean isContainOpername(String moduleid, String opername) {

		return isContainOpername(moduleid, opername, "-1l");
	}

	public String create(VcomSysOperation entity) throws SaveOrUpdateException {
		return (String) this.getSqlMapClientTemplate().insert(
				"vcomoper.create", entity);
	}

	public void delete(VcomSysOperation entity) throws DeleteException {
		this.getSqlMapClientTemplate().delete("vcomoper.delete",
				entity.getId());

	}

	public VcomSysOperation query(String id)
			throws ObjectNotFindException {
		return (VcomSysOperation) this.getSqlMapClientTemplate().queryForObject(
				"vcomoper.query",id);
	}

	public void saveOrupdate(VcomSysOperation entity)
			throws SaveOrUpdateException {
		this.getSqlMapClientTemplate().update("vcomoper.update", entity);

	}

}
