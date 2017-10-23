package zzvcom.log.pojo;

import java.util.Date;

/**
 * VcomSysLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VcomSysLog implements java.io.Serializable {

	// Fields

	private String id;
	private String userName;//操作用户
	private String userid;
	private String userIp;//用户IP
	private Date operateDate;//操作时间
	private int source; //-1:所有日志 1:操作日志 2:异常日志
	private String operateClass;
	private String errorDescription;
	private String messages;

	// Constructors

	/** default constructor */
	public VcomSysLog() {
	}

	/** full constructor */
	public VcomSysLog(String userName,String userid, String userIp, Date operateDate,
			int source, String operateLevel, String operateClass,
			String errorDescription, String messages) {
		this.userName = userName;
		this.userid = userid;
		this.userIp = userIp;
		this.operateDate = operateDate;
		this.source = source;
		this.operateClass = operateClass;
		this.errorDescription = errorDescription;
		this.messages = messages;
	}

	// Property accessors
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIp() {
		return this.userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public Date getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public int getSource()
    {
    	return source;
    }

	public void setSource(int source)
    {
    	this.source = source;
    }

	public String getOperateClass() {
		return this.operateClass;
	}

	public void setOperateClass(String operateClass) {
		this.operateClass = operateClass;
	}

	public String getErrorDescription() {
		return this.errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMessages() {
		return this.messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

}