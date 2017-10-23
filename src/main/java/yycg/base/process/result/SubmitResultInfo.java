package yycg.base.process.result;


/**
 * 系统提交结果类
 * @author Thinkpad
 *
 */
public class SubmitResultInfo {

	public SubmitResultInfo(ResultInfo resultInfo){
		this.resultInfo = resultInfo;
	}
	
	//操作结果信息
	private ResultInfo resultInfo;
	
	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
		
}
