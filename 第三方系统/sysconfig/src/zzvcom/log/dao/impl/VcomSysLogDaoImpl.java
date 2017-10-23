package zzvcom.log.dao.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import zzvcom.log.dao.VcomSysLogDao;
import zzvcom.log.pojo.VcomSysLog;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.util.PageUtil;

public class VcomSysLogDaoImpl extends SqlMapClientDaoSupport implements
		VcomSysLogDao {
	public static final Logger log = Logger.getLogger(VcomSysLogDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<VcomSysLog> getAllBySplitPage(PageUtil page) throws Exception {

		// String hql = "select vlog from VcomSysLog vlog order by id desc";
		//
		// Query query = getSession().createQuery(hql);
		// ScrollableResults sr = query.scroll();
		// sr.last();
		// page.setTotalRow(sr.getRowNumber() + 1);
		// sr.close();
		// query.setFirstResult(page.getStart());
		// query.setMaxResults(page.getEnd());
		//
		// return query.list();
		return null;

	}

	@SuppressWarnings("unchecked")
	public List<VcomSysLog> getByDateArea(String ip, int logType, String start,
			String end, PageUtil page) throws Exception {
		// String hql = "select vlog from VcomSysLog vlog where 1=1 ";
		// List<Object> listObj = new ArrayList<Object>();
		//
		// if (!ip.equals("")) {
		// hql += "and userIp=? ";
		// listObj.add(ip);
		// }
		// if (logType > 0) {
		// hql += "and source=? ";
		// listObj.add(logType);
		// }
		// if (start != null) {
		// hql += "and operateDate>=? ";
		// listObj.add(start);
		// }
		// if (end != null) {
		// hql += "and operateDate <= ? ";
		// listObj.add(end);
		// }
		// hql += "order by id desc";
		//
		// Object[] objects = new Object[listObj.size()];
		// for (int i = 0; i < listObj.size(); i++) {
		// objects[i] = listObj.get(i);
		// }
		//
		// Query query = createQuery(hql, objects);
		//
		// ScrollableResults sr = query.scroll();
		// sr.last();
		// page.setTotalRow(sr.getRowNumber() + 1);
		// sr.close();
		// query.setFirstResult(page.getStart());
		// query.setMaxResults(page.getEnd());
		//
		// return query.list();
		return null;
	}

	// 当前日期前几天或者后几天的日期
	public static String afterNDay(int n) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		String s = df.format(d2);
		return s;
	}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		c.add(Calendar.DATE, -5);
		Date d2 = c.getTime();
		Date now = new Date();

	}

	private static Date parseToDate(String strDate) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
		} catch (ParseException e) {
			System.out.print("转化" + strDate + "出错");
			e.printStackTrace();
			return null;
		}

	}

	private boolean isBlank(String str) {
		if (null == str || "".equals(str.trim()))
			return true;
		return false;
	}

	public int deleteLogByTime(String endTime) {
		// Session session = this.getSession();
		// Criteria cri = session.createCriteria(VcomSysLog.class);
		// cri.add(Restrictions.le("operateDate", parseToDate(endTime + "
		// 23:59:59")));
		// this.getHibernateTemplate().deleteAll(cri.list());
		// return 0;

		return this.getSqlMapClientTemplate().delete("vcomlog.deleteLogByTime",
				endTime + " 23:59:59");
		 
	}

	public List<VcomSysLog> getLogList(String ip, String logType,
			String startTime, String endTime, PageUtil page) {
		// //
		// Session session = this.getSession();
		// Criteria cri = session.createCriteria(VcomSysLog.class);
		//
		// // 日志类型
		// if (!"-1".equals(logType) && !isBlank(logType))
		// cri.add(Restrictions.eq("source", Integer.parseInt(logType)));
		//
		// // ip
		// if (null != ip && !"".equals(ip.trim()))
		// cri.add(Restrictions.like("userIp", "%" + ip + "%"));
		//
		// String startTime_end = " 00:00:00";// 时分秒
		// String endTime_end = " 23:59:59";// 时分秒
		//
		// // 创建时间
		// if (!isBlank(startTime) && !isBlank(endTime))// 开始时间，结束时间皆非空
		// cri.add(Restrictions.between("operateDate", parseToDate(startTime+
		// startTime_end), parseToDate(endTime + endTime_end)));
		// if (!isBlank(startTime) && isBlank(endTime))// 开始时间非空，结束时间为null
		// cri.add(Restrictions.ge("operateDate", parseToDate(startTime+
		// startTime_end)));
		// if (isBlank(startTime) && !isBlank(endTime))// 结束时间非空,开始时间为null
		// cri.add(Restrictions.le("operateDate", parseToDate(endTime +
		// endTime_end)));
		//
		// cri.addOrder(Order.desc("id"));
		// if (null == page)
		// return cri.list();
		//
		// // 分页
		// ScrollableResults sr = cri.scroll();
		// sr.last();
		// page.setTotalRow(sr.getRowNumber() + 1);
		// sr.close();
		//
		// cri.setFirstResult(page.getStart());
		// cri.setMaxResults(page.getEnd());
		//
		// return cri.list();

		Map map = new HashMap();

		// 日志类型
		if (!"-1".equals(logType) && !isBlank(logType))
			map.put("source", Integer.parseInt(logType));

		// ip

		map.put("userIp", StringEscapeUtils.escapeSql(ip));// cri.add(Restrictions.like("userIp",
		// "%" + ip
		// + "%"));

		String startTime_end = " 00:00:00";// 时分秒
		String endTime_end = " 23:59:59";// 时分秒

		// 创建时间
		if (StringUtils.isNotBlank(startTime))
			map.put("startTime", startTime + startTime_end);

		if (StringUtils.isNotBlank(endTime))
			map.put("endTime", endTime + endTime_end);

		if (null != page) {
			int count = (Integer) this.getSqlMapClientTemplate()
					.queryForObject("vcomlog.getLogListCount", map);
			page.setTotalRow(count);

			map.put("start", page.getStart());
			map.put("end", page.getEnd());
			map.put("rowend", page.getRowend());
		}
		return this.getSqlMapClientTemplate().queryForList(
				"vcomlog.getLogList", map);

	}

	public Long create(VcomSysLog entity) throws SaveOrUpdateException {
		return (Long) this.getSqlMapClientTemplate().insert(
				"vcomlog.create", entity);
	}

	public void delete(VcomSysLog entity) throws DeleteException {
		// TODO Auto-generated method stub

	}

	public VcomSysLog query(Serializable id) throws ObjectNotFindException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrupdate(VcomSysLog entity) throws SaveOrUpdateException {
		// TODO Auto-generated method stub

	}
}
