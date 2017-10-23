package zzvcom.log.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import zzvcom.util.PageUtil;

public class VcomSysLogAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(VcomSysLogAction.class);

	// 查询字段
	private String searchIp;
	private String logType;
	private String startTime;
	private String endTime;
	private static String loginedUser = "admin";// 登录的用户

	/**
	 * 获得日志列表
	 * 
	 * @return
	 */
	public String getLogPageUI() {

		PageUtil page = new PageUtil();
		page.setCurPage(this.getPageIndex());
		this.setLogList(this.getVcomSysLogService().getLogList(searchIp,
				logType, startTime, endTime, page));
		this.setPageBar(page.getToolsMenu());

		return SUCCESS;
	}

	/**
	 * 跳转至批量删除页面
	 * 
	 * @return
	 */
	public String toBatchDelLogPage() {
		Calendar calendar = Calendar.getInstance();
		// calendar.add(Calendar.DATE, -1); //得到前一天
		calendar.add(Calendar.MONTH, -1); // 得到前一个月

		this.startTime = new SimpleDateFormat("yyyy-MM-dd").format(calendar
				.getTime());

		return SUCCESS;
	}
	public String deleteLogBatch() {
		this.getVcomSysLogService().deleteLogByTime(endTime);
		request.setAttribute("isSucc", true);// 是否成功
		return SUCCESS;
	}

	public String getSearchIp() {
		return searchIp;
	}

	public void setSearchIp(String searchIp) {
		this.searchIp = searchIp;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 getRemoteIp .
	 * 
	 * @return string
	 */
	public static String getRemoteIp() {
		return ServletActionContext.getRequest().getRemoteAddr();
	}

}
