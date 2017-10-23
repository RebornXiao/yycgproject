package zzvcom.sys.action;

import java.util.ArrayList;
import java.util.List;

import zzvcom.sys.pojo.Dicttype;
import zzvcom.sys.service.ManagerSystemConfig;

public class SaveDictType extends BaseAction {

	private Dicttype dicttype;
	private String method;
	private String typecodes;
	private String oldtypecode;
private ManagerSystemConfig managerSystemConfig;
	
	public ManagerSystemConfig getManagerSystemConfig() {
		return managerSystemConfig;
	}
	public void setManagerSystemConfig(ManagerSystemConfig managerSystemConfig) {
		this.managerSystemConfig = managerSystemConfig;
	}
	public String execute() throws Exception {
		String forwardStr = SUCCESS;

		if (dicttype != null) {
			if ("add".equals(method)) {
				String gettypecode = dicttype.getTypecode();

				Dicttype selDicttype = managerSystemConfig
						.getDicttypeByCode(gettypecode);

				if (selDicttype != null) {
					forwardStr = "actionError";
					addActionError(getText("error.dictCodeRepeat"));
					return forwardStr;
				}

				managerSystemConfig.addDicttype(dicttype);

			} else if ("update".equals(method)) {
				String gettypecode = dicttype.getTypecode();
				if (gettypecode != null) {
					if (!gettypecode.equals(oldtypecode)) {
						Dicttype selDicttype = managerSystemConfig
								.getDicttypeByCode(gettypecode);
						if (selDicttype != null) {
							forwardStr = "actionError";
							addActionError(getText("error.dictCodeRepeat"));
							return forwardStr;
						}

					}
					managerSystemConfig.updateDicttype(dicttype);
				}

			}
			
		} else {
			if ("delete".equals(method)) {
				String[] codearry = typecodes.split(",");
				List codeList = new ArrayList();
				for (int i = 0; i < codearry.length; i++) {
					String id = codearry[i];
					codeList.add(id);
				}
				managerSystemConfig.delDicttype(codeList);
			}else{
				forwardStr = "actionError";
			}
			
		}
		return forwardStr;
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



	public String getTypecodes() {
		return typecodes;
	}

	public void setTypecodes(String typecodes) {
		this.typecodes = typecodes;
	}

	public String getOldtypecode() {
		return oldtypecode;
	}

	public void setOldtypecode(String oldtypecode) {
		this.oldtypecode = oldtypecode;
	}

}
