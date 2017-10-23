package zzvcom.sys.action;

import zzvcom.sys.pojo.Dicttype;
import zzvcom.sys.service.ManagerSystemConfig;

public class MagDictClass extends BaseAction {

	private Dicttype dicttype;
	private String method;
	private String typecode;
	private ManagerSystemConfig managerSystemConfig;
	
	public ManagerSystemConfig getManagerSystemConfig() {
		return managerSystemConfig;
	}
	public void setManagerSystemConfig(ManagerSystemConfig managerSystemConfig) {
		this.managerSystemConfig = managerSystemConfig;
	}
	public String execute() throws Exception { 
		
		if(method!=null&&method.equals("update")){
			dicttype = managerSystemConfig.getDicttypeByCode(typecode);
		}else{
			dicttype = new Dicttype();
			dicttype.setTypesort(0);
		}
      
		return SUCCESS;
	}
	public Dicttype getDicttype() {
		return dicttype;
	}
	public void setDicttype(Dicttype dicttype) {
		this.dicttype = dicttype;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	
	
}
