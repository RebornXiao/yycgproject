package zzvcom.sys.action;

import java.util.List;

import org.apache.log4j.Logger;

import zzvcom.log.util.LogUtil;
import zzvcom.sys.pojo.Basicinfo;
import zzvcom.sys.service.ManagerBasicInfo;

public class BasicinfoAction extends BaseAction {
	public static final Logger log = Logger.getLogger(BasicinfoAction.class);
	private List basicinfoList;
    private ManagerBasicInfo basicinfoService;
	public ManagerBasicInfo getBasicinfoService() {
		return basicinfoService;
	}

	public void setBasicinfoService(ManagerBasicInfo basicinfoService) {
		this.basicinfoService = basicinfoService;
	}

	public List getBasicinfoList() {
		return basicinfoList;
	}

	public void setBasicinfoList(List basicinfoList) {
		this.basicinfoList = basicinfoList;
	}

	public String findBasicinfoList() throws Exception {
		basicinfoList = basicinfoService.findeditprojectList();
		return SUCCESS;
	}

	public String updateBasicinfo() {
		String[] idArr=request.getParameterValues("id");
	    String[] valueArr=request.getParameterValues("value");
	    boolean flag=true;
	    if(idArr!=null){
    	   for(int i = 0; i < idArr.length; i++) {
    		   Basicinfo basicinfo=basicinfoService.findBasicinfoById(idArr[i]);
    		   basicinfo.setValue(valueArr[i]);
    		   try{
    			   basicinfoService.updateBasicinfo(basicinfo); 
    		   }catch(Exception e){
    			   flag=false;
    			   break;
    		   }
    	   }
    	   resulturl= "findBasicinfoList.action";
    	   if(flag){
    		   LogUtil.insert_log("修改系统配置成功", request); 
    		   request.setAttribute("message", "success");
    		   addActionMessage("修改系统配置成功");
    	   }else{
    		   LogUtil.insert_log("修改系统配置失败", request); 
    		   request.setAttribute("message", "error");
    		   addActionMessage("修改系统配置失败");
    		   return ERROR;
    	   }
	    }
	
		return SUCCESS;
	}
}
