package zzvcom.sys.action;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import zzvcom.sys.pojo.Dictinfo;
import zzvcom.sys.pojo.Dicttype;
import zzvcom.sys.service.ManagerSystemConfig;

public class MagDictClassInfo extends BaseAction {

	private Dictinfo dictinfo;
	private String method;
	private String dictid;
	private String typecode;

	private String classcode;
    private String codelength;
private ManagerSystemConfig managerSystemConfig;
	
	public ManagerSystemConfig getManagerSystemConfig() {
		return managerSystemConfig;
	}
	public void setManagerSystemConfig(ManagerSystemConfig managerSystemConfig) {
		this.managerSystemConfig = managerSystemConfig;
	}
	public String execute() throws Exception { 
		
		Dicttype dicttype = managerSystemConfig.getDicttypeByCode(typecode);
        codelength = dicttype.getCodelength();
		if(method!=null&&method.equals("update")){
			dictinfo = managerSystemConfig.getDictinfoById(dictid);
		}else{
			dictinfo = new Dictinfo();
			dictinfo.setTypename(dicttype.getTypename());
            dictinfo.setIsenable("1");
		}
		/*
		schoollist = new ArrayList();
		HttpSession session = getSession();
		ActiveUser activeuser = (ActiveUser) session.getAttribute("activeUser");
		String iscenteruser = activeuser.getIscenteruser();
		List tempschoollist=null;
		if("1".equals(iscenteruser)){
		 tempschoollist = MSF.getManagerArea().getAreaByArealevel("1");
		}else{
		 List userarealist = activeuser.getArealist();
		 tempschoollist = MSF.getManagerArea().findSelfAreaListByidlist(userarealist);
		}
		if(tempschoollist!=null&&tempschoollist.size()>1){
			Area firstArea = new Area();
			firstArea.setAreaid("");
			firstArea.setAreaname("请选择学校");
			schoollist.add(firstArea);
		}else if(tempschoollist!=null&&tempschoollist.size()==1){
			Area temparea = (Area) tempschoollist.get(0);
			classcode = temparea.getAreaid();
		}
		schoollist.addAll(tempschoollist);
      */
		return SUCCESS;
	}

	public Dictinfo getDictinfo() {
		return dictinfo;
	}

	public void setDictinfo(Dictinfo dictinfo) {
		this.dictinfo = dictinfo;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDictid() {
		return dictid;
	}
	public void setDictid(String dictid) {
		this.dictid = dictid;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}


	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}
    public String getCodelength()
    {
        return codelength;
    }
    public void setCodelength(String codelength)
    {
        this.codelength = codelength;
    }
	
}
