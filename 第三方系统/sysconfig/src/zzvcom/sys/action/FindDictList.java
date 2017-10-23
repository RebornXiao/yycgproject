package zzvcom.sys.action;

import java.util.List;

import zzvcom.sys.service.ManagerSystemConfig;

public class FindDictList extends BaseAction {
	
	private List reslist;
private ManagerSystemConfig managerSystemConfig;
	
	public ManagerSystemConfig getManagerSystemConfig() {
		return managerSystemConfig;
	}
	public void setManagerSystemConfig(ManagerSystemConfig managerSystemConfig) {
		this.managerSystemConfig = managerSystemConfig;
	}
	public String execute() throws Exception { 
		 
		reslist = managerSystemConfig.findDicttypelist();

		return SUCCESS;
	}

	public List getReslist() {
		return reslist;
	}

	public void setReslist(List reslist) {
		this.reslist = reslist;
	}

	
}
