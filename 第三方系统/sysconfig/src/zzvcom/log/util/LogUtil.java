package zzvcom.log.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zzvcom.log.pojo.VcomSysLog;
import zzvcom.log.service.VcomSysLogService;
import zzvcom.sys.pojo.VcomSysUser;

public class LogUtil {
	public static VcomSysLogService getLogService(HttpServletRequest request){
		ApplicationContext   ctx   =   WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		VcomSysLogService logService = (VcomSysLogService) ctx.getBean("vcomSysLogService");
		return logService;
	}
	/**
	 * 新增日志记录
	 *<b>方法描述</b>： <p>
	 *<b>方法流程</b>： <p>
	 *@param log 日志对象
	 */
	public static void insertLogInfo(HttpServletRequest request,VcomSysLog log){
		 VcomSysLogService logService=getLogService(request);
		 try {
			logService.saveSysLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * action捕获异常时新增日志记录
	 *<b>方法描述</b>： <p>
	 *<b>方法流程</b>： <p>
	 *@param request
	 *@param e
	 */
	public static void insertLogInfoForCatchEx(HttpServletRequest request,Exception e){
		VcomSysLogService logService = getLogService(request);
		VcomSysUser user = (VcomSysUser) request.getSession().getAttribute(
				"userInfo");
		VcomSysLog log = new VcomSysLog();
		log.setUserIp(request.getRemoteAddr());
		log.setUserName(user.getUsername());
		log.setUserid(user.getUsercode());
		log.setOperateDate(new Date());
		log.setSource(2);//2为异常日志
		log.setMessages(e.toString());
		try {
			logService.saveSysLog(log);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * 操作日志
	 * 
	 * @param message
	 *            操作内容
	 * @param request
	 * @return
	 */
	public static void insert_log(String message,HttpServletRequest request) {
		HttpSession session = request.getSession();
        String ip = request.getRemoteAddr();
        VcomSysUser user=(VcomSysUser) request.getSession().getAttribute("userInfo");
        VcomSysLog log=new VcomSysLog();
		VcomSysLogService logService =getLogService(request);
        log.setUserIp(ip);
        log.setUserName(user.getUsername());
        log.setUserid(user.getUsercode());
        log.setOperateDate(new Date());
        log.setSource(1);//1为操作日志
        log.setMessages(message);
        try {
			logService.saveSysLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
