package zzvcom.sys.action;

import java.util.ArrayList;
import java.util.List;

import zzvcom.sys.service.ManagerSystemConfig;

public class SetDictTypeState extends BaseAction {

	private String state;
	private String dictids;
	private ManagerSystemConfig managerSystemConfig;
	
	public ManagerSystemConfig getManagerSystemConfig() {
		return managerSystemConfig;
	}
	public void setManagerSystemConfig(ManagerSystemConfig managerSystemConfig) {
		this.managerSystemConfig = managerSystemConfig;
	}
	public String execute() throws Exception {
		String forwardStr = SUCCESS;
        List dictidlist = new ArrayList();
        String[] dictidArray = dictids.split(",");
        for (int m = 0; m < dictidArray.length; m++)
        {
            dictidlist.add(dictidArray[m].trim());
        }
        managerSystemConfig.updateSetDictinfoState(dictidlist,state);
		return forwardStr;
	}

	public String getDictids() {
		return dictids;
	}

	public void setDictids(String dictids) {
		this.dictids = dictids;
	}
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }

}
