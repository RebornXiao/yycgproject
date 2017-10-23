package zzvcom.sys.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import zzvcom.log.util.LogUtil;

/**
 * 
 * @author zhuhaifeng
 * 
 */
public class ReMsgUtil {
	public static final Logger log = Logger.getLogger(ReMsgUtil.class);

	/**
	 * 像request中插入信息
	 * 
	 * @param rm
	 * @param request
	 * @param moduleName
	 *            模块名称
	 * @param isLog
	 *            是否打印日志
	 */
	public static void putMsg(ReMsg rm, HttpServletRequest request,
			String moduleName, boolean isLog) {
		log.info("request is null?" + request == null);
		if (null == rm) {
			request.setAttribute("isSucc", true);// 是否成功

		} else {
			request.setAttribute("isSucc", rm.isSucc());// 是否成功
			request.setAttribute("message", rm.getMessage());// 信息
		}

		//
		if (isLog)
			LogUtil.insert_log(moduleName + rm.getMessage(), request);
		// log.info("isSucc:" + request.getAttribute("isSucc"));

	}

	/**
	 * 像request中插入信息
	 * 
	 * @param rm
	 * @param request
	 * @param moduleName
	 *            模块名称
	 * @param isLog
	 *            是否打印日志
	 */
	public static void putMsg(ReMsg rm, HttpServletRequest request,
			String moduleName) {
		putMsg(rm, request, moduleName, true);

	}

	/**
	 * 像request中插入信息
	 * 
	 * @param rm
	 * @param request
	 */
	public static void putMsg(ReMsg rm, HttpServletRequest request) {
		putMsg(rm, request, "", false);

	}

	private static String END = "\\r\\n";

	 /**
		 * 像request中插入信息
		 * 
		 * @param rm
		 * @param request
		 */
	public static void putMsg(ReMsg[] rms, HttpServletRequest request) {

		putMsg(rms, request, "", false);

	}

	/**
	 * 像request中插入信息
	 * 
	 * @param rms
	 * @param request
	 * @param moduleName
	 *            模块名称
	 */
	public static void putMsg(ReMsg[] rms, HttpServletRequest request,
			String moduleName) {

		putMsg(rms, request, moduleName, true);

	}

	/**
	 * 像request中插入信息
	 * 
	 * @param rms
	 * @param request
	 * @param moduleName
	 *            模块名称
	 * @param isLog
	 */
	public static void putMsg(ReMsg[] rms, HttpServletRequest request,
			String moduleName, boolean isLog) {

		if (null == rms) {
			request.setAttribute("isSucc", true);// 是否成功

		} else {
			String msg = END;
			boolean flag = true;
			for (ReMsg rm : rms) {
				if (!rm.isSucc()) {
					flag = false;
					msg += rm.getMessage() + END;
				}

				if (isLog)
					LogUtil.insert_log(moduleName + rm.getMessage(), request);
			}// end of for
			request.setAttribute("isSucc", flag);// 是否成功
			request.setAttribute("message", msg);// 信息
			// if (rm.isSucc())// 像后台输出记录
			// LogUtil.info(request, rm.getMessage());
		}

	}

	/**
	 * 判断返回信息是否正常，true-正常；false-不正常
	 * 
	 * @param rm
	 * @return
	 */
	public static boolean isSuccess(ReMsg rm) {
		if (null == rm)
			return true;
		else
			return rm.isSucc();

	}

	/**
	 * 创建信息
	 * 
	 * @param name
	 * @param message
	 * @return
	 */
	public static String createMessage(String name, String message) {
		return "[" + name + "]" + message;

	}
}
