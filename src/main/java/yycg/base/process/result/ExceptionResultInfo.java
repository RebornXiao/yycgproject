package yycg.base.process.result;


/**
 * 自定义系统异常类
 * @author mrt
 *
 */
public class ExceptionResultInfo extends Exception {

	/**
	 * 系统提示信息
	 */
	private ResultInfo resultInfo;
	
	public ExceptionResultInfo(ResultInfo resultInfo) {
		super(resultInfo.getMessage());
		this.resultInfo = resultInfo;
	}

	//get方法在转json时使用
	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

}
