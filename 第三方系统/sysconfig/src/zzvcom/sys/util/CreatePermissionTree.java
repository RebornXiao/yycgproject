package zzvcom.sys.util;

import java.util.List;

import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysOperation;

public class CreatePermissionTree {
	public static String createTree(List moduleList,List operList,int type){
		String retTree = "var rootTree = new WebFXTree('"+(type==1?"未":"已")+"分配权限',null,'other');";
		if (moduleList != null && moduleList.size() > 0) {
			for (int i = 0; i < moduleList.size(); i++) {
				VcomSysModule model = (VcomSysModule) moduleList.get(i);
				retTree += "\n var s_" + model.getId() + " = new WebFXTreeItem(\"" + model.getModulename() + "\", null,null,'m_" + model.getId() + "');";
				if (model.getDepth() == 1)
					retTree += "\n rootTree.add(s_" + model.getId() + ");";
				else
					retTree += "\n s_" + model.getParentid() + ".add(s_" + model.getId() + ");";
			}
		
		
	        for (int j=0;j<operList.size();j++) {
	            VcomSysOperation oper=(VcomSysOperation) operList.get(j);
	            retTree += "\n var c_" + oper.getId() + " = new WebFXTreeItem(\"" + oper.getOpername() + "\", null,null,'o_"+oper.getId()+"');";
	            retTree += "\n s_"+oper.getModuleid()+".add(c_" + oper.getId() + ");";
	        }
		}
	        retTree += "document.write(rootTree);\n" + "rootTree.expand();";
	        return retTree;
	}
	
}
