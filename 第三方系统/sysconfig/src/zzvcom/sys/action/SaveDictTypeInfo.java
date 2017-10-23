package zzvcom.sys.action;

import java.util.ArrayList;
import java.util.List;

import zzvcom.log.util.MyUtil;
import zzvcom.log.util.UUIDBuild;
import zzvcom.sys.pojo.Dictinfo;
import zzvcom.sys.pojo.DictinfoExample;
import zzvcom.sys.service.ManagerSystemConfig;

public class SaveDictTypeInfo extends BaseAction {

	private Dictinfo dictinfo;
	private String method;
	private String dictids;
	private String typecode;
	private String olddictcode;
private ManagerSystemConfig managerSystemConfig;
	
	public ManagerSystemConfig getManagerSystemConfig() {
		return managerSystemConfig;
	}
	public void setManagerSystemConfig(ManagerSystemConfig managerSystemConfig) {
		this.managerSystemConfig = managerSystemConfig;
	}
	public String execute() throws Exception {
		String forwardStr = SUCCESS;
        String crutime = MyUtil.getDate();
		if (dictinfo != null) {
		    dictinfo.setInfo(dictinfo.getInfo().trim());
			dictinfo.setTypecode(typecode);
			dictinfo.setUpdatetime(crutime);
			if ("add".equals(method)) {
				Dictinfo vliDictinfo = new Dictinfo();
                vliDictinfo.setTypecode(typecode);
				vliDictinfo.setDictcode(dictinfo.getDictcode());
				vliDictinfo.setInfo(dictinfo.getInfo());
				//vliDictinfo.setSchoolcode(dictinfo.getSchoolcode());
				List validatelist = managerSystemConfig.getDictinfoByDictinfopars(vliDictinfo);
				if(validatelist!=null&&validatelist.size()>0){
					addActionError("该分类名称或者分类代码已经存在,请更改！");
					forwardStr = "actionError";
					return forwardStr;
				}
				//校验排序号是否重复
				DictinfoExample dictinfoexample=new DictinfoExample();
				DictinfoExample.Criteria dictinfoCri=dictinfoexample.createCriteria();
				dictinfoCri.andTypecodeEqualTo(typecode);
				dictinfoCri.andDictsortEqualTo(dictinfo.getDictsort());
				List validatelist2 = managerSystemConfig.getDictinfoByDictinfopars(dictinfoexample);
				if(validatelist2!=null&&validatelist2.size()>0){
					addActionError("显示顺序已经存在,请更改！");
					forwardStr = "actionError";
					return forwardStr;
				}
				//dictinfo.setDictcode(UUIDBuild.getUUID());
				dictinfo.setDictcode(dictinfo.getDictcode());
				managerSystemConfig.addDictinfo(dictinfo);

			} else if ("update".equals(method)) {
				Dictinfo vliDictinfo = new Dictinfo();
                vliDictinfo.setTypecode(typecode);
				vliDictinfo.setDictcode(dictinfo.getDictcode());
				vliDictinfo.setInfo(dictinfo.getInfo());
				//vliDictinfo.setSchoolcode(dictinfo.getSchoolcode());
				dictinfo.setDictcode(dictinfo.getDictcode());
				vliDictinfo.setId(dictinfo.getId());
				List validatelist = managerSystemConfig.getDictinfoByDictinfopars(vliDictinfo);
				if(validatelist!=null&&validatelist.size()>0){
					addActionError("该分类名称或者分类代码已经存在,请更改！");
					forwardStr = "actionError";
					return forwardStr;
				}
				//校验排序号是否重复
				DictinfoExample dictinfoexample=new DictinfoExample();
				DictinfoExample.Criteria dictinfoCri=dictinfoexample.createCriteria();
				dictinfoCri.andTypecodeEqualTo(typecode);
				dictinfoCri.andDictsortEqualTo(dictinfo.getDictsort());
				dictinfoCri.andIdNotEqualTo(dictinfo.getId());
				List validatelist2 = managerSystemConfig.getDictinfoByDictinfopars(dictinfoexample);
				if(validatelist2!=null&&validatelist2.size()>0){
					addActionError("显示顺序已经存在,请更改！");
					forwardStr = "actionError";
					return forwardStr;
				}
				managerSystemConfig.updateDictinfo(dictinfo);

			}

		} else {
			if ("delete".equals(method)) {
				String[] codearry = dictids.split(",");
				List codeList = new ArrayList();
				for (int i = 0; i < codearry.length; i++) {
					String id = codearry[i];
					codeList.add(id);
				}
				managerSystemConfig.delDictinfo(codeList);
			} else {
				addActionError("参数错误！");
				forwardStr = "actionError";
			}

		}
		return forwardStr;
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

	public String getDictids() {
		return dictids;
	}

	public void setDictids(String dictids) {
		this.dictids = dictids;
	}



	public String getOlddictcode() {
		return olddictcode;
	}

	public void setOlddictcode(String olddictcode) {
		this.olddictcode = olddictcode;
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

}
