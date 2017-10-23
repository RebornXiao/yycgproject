package zzvcom.log.dao;

import java.util.Date;
import java.util.List;

import zzvcom.log.pojo.VcomSysLog;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.util.PageUtil;

public interface VcomSysLogDao extends GenericDao<VcomSysLog> {
	/**
	 * 根据分页查询日志信息
	 * 
	 * @param page
	 * @return
	 */
	public List<VcomSysLog> getAllBySplitPage(PageUtil page) throws Exception;

	/**
	 * 根据时间区间查询日志信息
	 * 
	 * @param start
	 * @param end
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<VcomSysLog> getByDateArea(String ip, int logType, String start,
			String end, PageUtil page) throws Exception;

	/**
	 * 获得日志列表
	 * 
	 * @param ip
	 * @param logType
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<VcomSysLog> getLogList(String ip, String logType,
			String startTime, String endTime, PageUtil page);

	/**
	 * 删除endtime之前的所有日志
	 * 
	 * @param endTime
	 * @return
	 */
	public int deleteLogByTime(String endTime);

}
