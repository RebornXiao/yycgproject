package zzvcom.log.service;

import java.util.Date;
import java.util.List;

import zzvcom.log.pojo.VcomSysLog;
import zzvcom.util.PageUtil;

public interface VcomSysLogService {
	/**
	 * 添加日志信息接口
	 * 
	 * @param vcomSysLog
	 * @throws Exception
	 */
	public void saveSysLog(VcomSysLog entity) throws Exception;

	/**
	 * 
	 * 添加日志信息接口
	 * 
	 * @param userName
	 *            管理员姓名
	 * @param ip
	 *            管理员IP地址
	 * @param source
	 *            信息来源
	 * @param operateClass
	 *            操作类
	 * @param errorDescription
	 *            出错信息描述
	 * @param exceptionMsg
	 *            异常信息
	 * @throws Exception
	 * 
	 */
	public void saveLog(String userName, String ip, int source,
			String operateClass, String messages, String errorMessage)
			throws Exception;

	/**
	 * 获取所有日志信息接口
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<VcomSysLog> getAllLogs(PageUtil page) throws Exception;

	/**
	 * 根据时间区间获取日志信息接口
	 * 
	 * @param start
	 * @param end
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<VcomSysLog> getLogsByDateArea(String ip, int logType,
			String start, String end, PageUtil page) throws Exception;

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
