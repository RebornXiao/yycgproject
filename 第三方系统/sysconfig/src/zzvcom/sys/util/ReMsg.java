package zzvcom.sys.util;

/**
 * 返回状态
 * 
 * @author zhuhaifeng
 * 
 */
public class ReMsg {
	private String message = null;// 返回信息;

	private boolean isSucc = true;// true 成功，false失败

	/**
	 * 构造“表示成功”返回状态
	 */
	public ReMsg() {

	}

	/**
	 * 构造“表示失败”的返回状态,message中存储的为错误信息
	 * 
	 * @param message
	 *            错误信息
	 */
	public ReMsg(String message) {

		this.message = message;
		this.isSucc = false;
	}

	public ReMsg(String name, String message) {

		this.message = "[" + name + "]" + message;
		this.isSucc = false;
	}
	
	public ReMsg(String name, String message,boolean isSucc) {
		
		this.message = "[" + name + "]" + message;
		this.isSucc = isSucc;
	}

	/**
	 * 构造自定义返回状态
	 * 
	 * @param message
	 *            存储信息
	 * @param isSucc
	 *            成功标记
	 */
	public ReMsg(String message, boolean isSucc) {

		this.message = message;
		this.isSucc = isSucc;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSucc() {
		return isSucc;
	}
}
