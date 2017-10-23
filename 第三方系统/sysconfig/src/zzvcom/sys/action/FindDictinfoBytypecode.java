package zzvcom.sys.action;

import java.util.List;

import org.logicalcobwebs.proxool.Vcom_3DES;

import zzvcom.sys.pojo.Dicttype;
import zzvcom.sys.service.ManagerSystemConfig;

public class FindDictinfoBytypecode extends BaseAction {

	private List reslist;
	private String typecode;
	private String typename;
	private String sessionid;
	private String isshow = "";
private ManagerSystemConfig managerSystemConfig;
	
	public ManagerSystemConfig getManagerSystemConfig() {
		return managerSystemConfig;
	}
	public void setManagerSystemConfig(ManagerSystemConfig managerSystemConfig) {
		this.managerSystemConfig = managerSystemConfig;
	}
	public String execute() throws Exception {

		// System.out.println("typecode==="+typecode);
		if(sessionid != null && !sessionid.equals("")){
			//解析跳转过关的加密串
			Vcom_3DES tempDe= new Vcom_3DES(0,sessionid,"123456789012345678901234");
			String strTempDe = tempDe.Vcom3DESChiper();
			String[] sessionid_s = strTempDe.split("#");
			if(sessionid_s.length!=2)
					return "actionError";
			typecode = sessionid_s[0];
			Dicttype dicttype = managerSystemConfig.getDicttypeByCode(typecode);
			if(dicttype==null){
				typecode = null;
			}else{
				typename = dicttype.getTypename();
			}
			
		}
		if (typecode != null) {

				reslist = managerSystemConfig.findDicttypeinfolist(
						typecode);
				Dicttype dicttype = managerSystemConfig.getDicttypeByCode(typecode);
				if(null != dicttype.getCodelength() && !"".equals(dicttype.getCodelength())){
				    isshow = "yes";
				}
		} else {
			return "actionError";
		}

		return SUCCESS;
	}

	public List getReslist() {
		return reslist;
	}

	public void setReslist(List reslist) {
		this.reslist = reslist;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
    public String getIsshow()
    {
        return isshow;
    }
    public void setIsshow(String isshow)
    {
        this.isshow = isshow;
    }
	public String getTypename() {
		return typename;
	}

}
