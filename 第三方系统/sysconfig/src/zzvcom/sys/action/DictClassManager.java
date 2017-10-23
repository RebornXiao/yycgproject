package zzvcom.sys.action;

import java.util.List;

public class DictClassManager extends BaseAction {
	
	private List reslist;

	public String execute() throws Exception { 
		 
		//reslist = MSF.getManagersystemconfig().findDicttypelist();

		return SUCCESS;
	}

	public List getReslist() {
		return reslist;
	}

	public void setReslist(List reslist) {
		this.reslist = reslist;
	}

	
}
