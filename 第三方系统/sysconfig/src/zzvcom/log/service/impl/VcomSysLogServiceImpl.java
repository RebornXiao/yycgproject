package zzvcom.log.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import zzvcom.log.dao.VcomSysLogDao;
import zzvcom.log.pojo.VcomSysLog;
import zzvcom.log.service.VcomSysLogService;
import zzvcom.util.PageUtil;

public class VcomSysLogServiceImpl implements VcomSysLogService {
	public static final Logger log = Logger
			.getLogger(VcomSysLogServiceImpl.class);

	private VcomSysLogDao vcomSysLogDao;

	public VcomSysLogDao getVcomSysLogDao() {
		return vcomSysLogDao;
	}

	public void setVcomSysLogDao(VcomSysLogDao vcomSysLogDao) {
		this.vcomSysLogDao = vcomSysLogDao;
	}

	/**
	 * 添加日志信息接口
	 * 
	 * @param vcomSysLog
	 * @throws Exception
	 */
	public void saveSysLog(VcomSysLog entity) throws Exception {
		vcomSysLogDao.create(entity);
	}

	/**
	 * 
	 * 添加日志信息
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
			String operateClass, String messages, String errorMessage)throws Exception {
		VcomSysLog vlog = new VcomSysLog();
		vlog.setUserName(userName);
		vlog.setUserIp(ip);
		vlog.setSource(source);
		vlog.setOperateDate(new Date());
		vlog.setOperateClass(operateClass);
		vlog.setErrorDescription(errorMessage);
		vlog.setMessages(messages);

		vcomSysLogDao.create(vlog);

	}

	/**
	 * 获取所有日志信息
	 * 
	 * @param page
	 * @return list
	 */
	public List<VcomSysLog> getAllLogs(PageUtil page) throws Exception {
		return vcomSysLogDao.getAllBySplitPage(page);
	}

	/**
	 * 根据时间区间获取日志信息
	 * 
	 * @param start
	 * @param end
	 * @return list
	 */
	public List<VcomSysLog> getLogsByDateArea(String ip, int logType,
			String start, String end, PageUtil page) throws Exception {
		return vcomSysLogDao.getByDateArea(ip, logType, start, end, page);
	}

	public List<VcomSysLog> getLogList(String ip, String logType,
			String startTime, String endTime, PageUtil page) {
		 
		return vcomSysLogDao.getLogList(ip, logType, startTime, endTime, page);
	}
	/**
	 * 删除endtime之前的所有日志
	 * 
	 * @param endTime
	 * @return
	 */
	public int deleteLogByTime(String endTime){
		return vcomSysLogDao.deleteLogByTime(endTime);
	}
}
