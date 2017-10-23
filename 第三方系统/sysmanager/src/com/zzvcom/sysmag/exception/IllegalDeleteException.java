package com.zzvcom.sysmag.exception;

/**
 * 当应用程序试图删除一个对象，但该对象为其他对象所使用时，抛出IllegalDeleteException
 * @author Wang Xiaoming
 */
public class IllegalDeleteException extends RuntimeException {
	
	/**
	 * 构造器
	 * @param object 传入删除对象
	 */
	public IllegalDeleteException(Object object) {
		super("Delete Object " + object.toString() + " Failed. ");
	}

	public IllegalDeleteException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IllegalDeleteException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IllegalDeleteException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IllegalDeleteException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
}
	

