package zzvcom.sys.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import zzvcom.log.util.LogUtil;
import zzvcom.log.util.PropertyManager;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.util.CreatePermissionTree;
import zzvcom.sys.util.GetPermission;
import zzvcom.sys.util.ReMsg;
import zzvcom.sys.util.ReMsgUtil;
import zzvcom.sys.util.TreeForm;
import zzvcom.util.PageUtil;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @author lzq.
 * 
 */
public class RoleAction extends BaseAction implements ModelDriven<VcomSysRole> {

	private static final long serialVersionUID = 2327885481039764243L;
	public static final Logger log = Logger.getLogger(RoleAction.class);

	private String roleid;// 角色id
	private String platid;
	private String userid;
	private String permissionjson;
	private String isxuexicenter = "0";
	
	public String getIsxuexicenter()
    {
        return isxuexicenter;
    }
    public void setIsxuexicenter(String isxuexicenter)
    {
        this.isxuexicenter = isxuexicenter;
    }
    public String getPermissionjson() {
		return permissionjson;
	}
	public void setPermissionjson(String permissionjson) {
		this.permissionjson = permissionjson;
	}
	public String getPlatid() {
		return platid;
	}
	public void setPlatid(String platid) {
		this.platid = platid;
	}
	/**
	 * 跳转至新增或修改角色按钮
	 * @return
	 * @throws ObjectNotFindException
	 */
	public String toSaveOrUpdateRolePage() throws ObjectNotFindException {
		if (1 == operFlag) {
			this.setRoleobj(this.getRoleService().query(roleid));// 取出即将修改的数据
		}
		VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession().get("userInfo");
		if(usr != null && "2".equals(usr.getGroupid())){
		    isxuexicenter = "1";
        }
		return SUCCESS;
	}
	/**
	 * 保存角色
	 * @return
	 * @throws SaveOrUpdateException
	 */
	public String saveRole() {
		try {
		    VcomSysRole currrole = getRoleobj();
		    VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession().get("userInfo");
		    if(usr != null){
		        if("2".equals(usr.getGroupid())){
		            currrole.setOwner(usr.getSysid());
		        }else{
		            currrole.setOwner("0");
		        }
	        }
			ReMsgUtil.putMsg(getRoleService().saveRole(currrole),request);
			LogUtil.insert_log("添加角色成功!", request);
		} catch (SaveOrUpdateException e) {
			ReMsgUtil.putMsg(new ReMsg("", "添加角色失败！"),request);
			LogUtil.insert_log("添加角色失败!", request);
			e.printStackTrace();
		}
		return INPUT;
	}

	/**
	 * 修改角色
	 * @return
	 * @throws SaveOrUpdateException
	 */
	public String updateRole() {
		try {
			ReMsgUtil.putMsg(getRoleService().updateRole(getRoleobj()), request);
			LogUtil.insert_log("修改角色成功!", request);
		} catch (SaveOrUpdateException e) {
			ReMsgUtil.putMsg(new ReMsg("", "修改角色失败！"),request);
			LogUtil.insert_log("修改角色失败!", request);
			e.printStackTrace();
		}
		return INPUT;
	}/**
	 * 修改角色
	 * @return
	 * @throws SaveOrUpdateException
	 */
	public String updateUserPermissions() {
		try {
			String permissions = user.getPermissions();
			user = this.getUserService().query(user.getId());
			user.setPermissions(permissions);
			this.getUserService().updateUser(user);
			LogUtil.insert_log("保存权限成功!", request);
		} catch (Exception e) {
			ReMsgUtil.putMsg(new ReMsg("", "保存权限失败！"),request);
			LogUtil.insert_log("保存权限失败!", request);
			e.printStackTrace();
		}
		return INPUT;
	}
	/**
	 * 修改校验角色是否是初始化角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public void roleEditCheck() throws ObjectNotFindException {
		try {
		    boolean flag = this.getRoleService().checkRoleCode(roleid);
            VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession()
            .get("userInfo");
			if (null != usr && "admin".equals(usr.getUsercode())) {
				response.getWriter().write("true");
			} else if(flag){
				response.getWriter().write("true");
			} else {
                response.getWriter().write("false");
            }

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
     * 删除校验角色是否是初始化角色或者是否已经使用
     * 
     * @return
     * @throws Exception
     */
    public void roleDeleteCheck() throws ObjectNotFindException {
        try {
        boolean flag = this.getRoleService().checkRoleCode(roleid);
        boolean isUsedRole = this.getRoleService().checkRoleIsUsed(roleid);
        
            if (isUsedRole) {
                response.getWriter().write("false");
            } else if(flag){
                response.getWriter().write("true");
            } else {
                response.getWriter().write("false");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * 删除角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRole() throws ObjectNotFindException  {
		try{
			String[] checkboxIds = ServletActionContext.getRequest().getParameterValues("checkboxId");
			if (null != checkboxIds && 0 != checkboxIds.length) {
				ReMsg[] rms = new ReMsg[checkboxIds.length];
				int i = 0;
				for (String checkboxId : checkboxIds) {
					rms[i] = getRoleService().deleteRole(checkboxId);
					i++;
				}// end of for
				ReMsgUtil.putMsg(rms, request);
			}
			LogUtil.insert_log("删除角色成功!", request);
		}catch (ObjectNotFindException e) {
			ReMsgUtil.putMsg(new ReMsg("", "删除角色失败！"),request);
			LogUtil.insert_log("删除角色失败!", request);
		} catch (NumberFormatException e) {
			ReMsgUtil.putMsg(new ReMsg("", "删除角色失败！"),request);
			LogUtil.insert_log("删除角色失败!", request);
		} catch (DeleteException e) {
			ReMsgUtil.putMsg(new ReMsg("", "删除角色失败！"),request);
			LogUtil.insert_log("删除角色失败!", request);
		}
		return getRoleUI();
	}
	/**
	 * 得到所有的角色
	 * 
	 * @return
	 */
	public String getRoleUI(){
        VcomSysRole currrole = this.getRoleobj();
	    VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession().get("userInfo");
	    if(usr != null){
	        if(usr.getSysid() ==null || "".equals(usr.getSysid())){
	            currrole.setOwner("0");
	        }else{
	            currrole.setOwner(usr.getSysid());
	        }
        }
	    currrole.setExtstr("type != '4'");
		PageUtil sp=new PageUtil(this.getPageIndex());
		this.setRoleList(getRoleService().getAllBySplitPage(sp,currrole));
		this.setPageBar(sp.getToolsMenu());
		return SUCCESS;
	}

	/**
	 * 用户检索
	 * 
	 * @return
	 */
	public String getRoleSearch() {
		return this.getRoleUI();
	}

	/**
	 * 进入权限分配页面
	 * 
	 * @return
	 */
	public String getRollPermissionUI() {
		this.getRoleobj().setUpdatetime(new Date());
		this.setRoleList(this.getRoleService().getAllRole());
		return SUCCESS;
	}
	/**
	 * 根据平台获取对应的权限
	 * @return
	 */
	public String getPlatModule(){
		
		List<VcomSysModule> modellist=new ArrayList();
		VcomSysUser user=(VcomSysUser)this.request.getSession().getAttribute("userInfo");
		//如果是学习中心用户登录
		if(user!=null&&user.getGroupid().equals("2")){
			List<VcomSysRole> rolelist=this.getRoleService().getRoleByIds(user.getRole());
			String ids="";
			//per=user.getPermissions();
			for (VcomSysRole us : rolelist) {//组合角色权限
				ids=ids+us.getPermissions()+",";
			}
			modellist=this.getModuleService().getModuleListByIds(GetPermission.getPer(ids,true,true), true);
		}else{
			modellist =this.getModuleService().getModuleList();
		}
		
		
		List persionlist=new ArrayList();
		if(this.getRoleobj().getId()!=null && !this.getRoleobj().getId().equals("")){
			try {
				//System.out.println("fffff="+this.getRoleobj().getId());
				this.setRoleobj(this.getRoleService().query(this.getRoleobj().getId()));
				this.getRoleobj().setPermissions(","+this.getRoleobj().getPermissions()+",");
			} catch (ObjectNotFindException e) {
				e.printStackTrace();
			}
		}

		for(int i=modellist.size()-1;i>=0;i--){
			VcomSysModule firstmodel=(VcomSysModule) modellist.get(i);
			
			if(!firstmodel.getParentid().equals("-1")){
				for(int j=modellist.size()-1;j>=0;j--){
					VcomSysModule nextmodel=(VcomSysModule) modellist.get(j);
					if(firstmodel.getParentid().equals(nextmodel.getId())){
						TreeForm tree=new TreeForm();
						tree.setId(firstmodel.getId().toString());
						tree.setText(this.getText(firstmodel.getModulename()));
						tree.setIcon("../../images/icon-1.png");
						tree.setParentid(firstmodel.getParentid());
						tree.setChecked(false);
						if(this.getRoleobj().getPermissions()!=null&&!this.getRoleobj().getPermissions().equals("")){
							if(this.getRoleobj().getPermissions().indexOf(","+tree.getId()+",")>=0){
								tree.setChecked(true);
							}else if(this.getRoleobj().getPermissions().indexOf(","+firstmodel.getId()+",")>=0){
								tree.setChecked(true);
							}
						}
						if(firstmodel.getChildren().size()==0)
							tree.setLeaf(true);
						else {
							tree.setLeaf(false);						
							List _l=firstmodel.getChildren();
							for(int m=_l.size()-1;m>=0;m--){
								tree.getChildren().add(_l.get(m));
							}
						}
						
						nextmodel.getChildren().add(tree);
						modellist.remove(i);
						break;
					}
				}
			}else{
				TreeForm tree=new TreeForm();
				tree.setId(firstmodel.getId().toString());
				tree.setText(this.getText(firstmodel.getModulename()));
				tree.setIcon("../../images/icon-1.png");
				tree.setChecked(false);
				if(this.getRoleobj().getPermissions()!=null&&!this.getRoleobj().getPermissions().equals("")){
					if(this.getRoleobj().getPermissions().indexOf(","+tree.getId()+",")>=0){
						tree.setChecked(true);
					}else if(this.getRoleobj().getPermissions().indexOf(","+firstmodel.getId()+",")>=0){
						tree.setChecked(true);
					}
				}
				if(firstmodel.getLink()!=null)
					tree.setHref(firstmodel.getLink().indexOf("?")>=0?(firstmodel.getLink()+"&mid="+firstmodel.getId()):(firstmodel.getLink()+"?mid="+firstmodel.getId()));
				if(firstmodel.getChildren().size()==0)
					tree.setLeaf(true);
				else {
					tree.setLeaf(false);
					List _l=firstmodel.getChildren();
					for(int m=_l.size()-1;m>=0;m--){
						tree.getChildren().add(_l.get(m));
					}
				}
				PropertyManager property = new PropertyManager("Group.properties");
                String modelid= property.getPropertyStr(this.getPlatid());//取出用户组对应的模块根id
                if(user!=null&&!user.getGroupid().equals("2")){
                	if(firstmodel.getId().equals(modelid))persionlist.add(tree);
                }else{
                	persionlist.add(tree);
                }
                
			}
		}
		try {
			permissionjson=JSONUtil.serialize(persionlist,null,null,false,false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}
public String getPlatUserModule(){
	VcomSysUser curruser = null;
		List<VcomSysModule> modellist=new ArrayList();
		VcomSysUser user=(VcomSysUser)this.request.getSession().getAttribute("userInfo");

		//如果是学习中心用户登录
		if(user!=null&&user.getGroupid().equals("2")){
			String ids="";
			/*
			List<VcomSysRole> rolelist=this.getRoleService().getRoleByIds(user.getRole());
			
			//per=user.getPermissions();
			for (VcomSysRole us : rolelist) {//组合角色权限
				ids=ids+us.getPermissions()+",";
			}*/
			ids=user.getPermissions();
			modellist=this.getModuleService().getModuleListByIds(GetPermission.getPer(ids,true,true), true);
		}else{
			modellist =this.getModuleService().getModuleList();
		}


		List persionlist=new ArrayList();
		String currpermissions = "";
		try {
			curruser = this.getUserService().query(userid);
			currpermissions=","+curruser.getPermissions()+",";
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(int i=modellist.size()-1;i>=0;i--){
			VcomSysModule firstmodel=(VcomSysModule) modellist.get(i);
			
			if(!firstmodel.getParentid().equals("-1")){
				for(int j=modellist.size()-1;j>=0;j--){
					VcomSysModule nextmodel=(VcomSysModule) modellist.get(j);
					if(firstmodel.getParentid().equals(nextmodel.getId())){
						TreeForm tree=new TreeForm();
						tree.setId(firstmodel.getId().toString());
						tree.setText(this.getText(firstmodel.getModulename()));
						tree.setIcon("../../images/icon-1.png");
						tree.setParentid(firstmodel.getParentid());
						tree.setChecked(false);
						if(!currpermissions.equals("")){
							if(currpermissions.indexOf(","+tree.getId()+",")>=0){
								tree.setChecked(true);
							}else if(currpermissions.indexOf(","+firstmodel.getId()+",")>=0){
								tree.setChecked(true);
							}
						}
						if(firstmodel.getChildren().size()==0)
							tree.setLeaf(true);
						else {
							tree.setLeaf(false);						
							List _l=firstmodel.getChildren();
							for(int m=_l.size()-1;m>=0;m--){
								tree.getChildren().add(_l.get(m));
							}
						}
						
						nextmodel.getChildren().add(tree);
						modellist.remove(i);
						break;
					}
				}
			}else{
				TreeForm tree=new TreeForm();
				tree.setId(firstmodel.getId().toString());
				tree.setText(this.getText(firstmodel.getModulename()));
				tree.setIcon("../../images/icon-1.png");
				tree.setChecked(false);
				if(!currpermissions.equals("")){
					if(currpermissions.indexOf(","+tree.getId()+",")>=0){
						tree.setChecked(true);
					}else if(currpermissions.indexOf(","+firstmodel.getId()+",")>=0){
						tree.setChecked(true);
					}
				}
				if(firstmodel.getLink()!=null)
					tree.setHref(firstmodel.getLink().indexOf("?")>=0?(firstmodel.getLink()+"&mid="+firstmodel.getId()):(firstmodel.getLink()+"?mid="+firstmodel.getId()));
				if(firstmodel.getChildren().size()==0)
					tree.setLeaf(true);
				else {
					tree.setLeaf(false);
					List _l=firstmodel.getChildren();
					for(int m=_l.size()-1;m>=0;m--){
						tree.getChildren().add(_l.get(m));
					}
				}
				String modelid = "71";
				//PropertyManager property = new PropertyManager("Group.properties");
                //String modelid= property.getPropertyStr(this.getPlatid());//取出用户组对应的模块根id
				if("1".equals(curruser.getGroupid())){
					modelid = "71";
				}else if("2".equals(curruser.getGroupid())){
					modelid = "72";
				}else if("0".equals(curruser.getGroupid())){
					modelid = "1";
				}
                if(user!=null&&!user.getGroupid().equals("2")){
                	if(firstmodel.getId().toString().equals(modelid))persionlist.add(tree);
                }else{
                	persionlist.add(tree);
                }
                
			}
		}
		try {
			permissionjson=JSONUtil.serialize(persionlist,null,null,false,false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}
	/**
	 * 权限分配未分配权限
	 * 
	 * @return
	 */
	public String getAllPermissionTree() {
		List<VcomSysModule> modellist = new ArrayList<VcomSysModule>();
		List<VcomSysOperation> operList = new ArrayList<VcomSysOperation>();
		if (this.getRoleobj().getId() == null) {
			modellist = this.getModuleService().getModuleList();
			//modellist = this.getModuleService().getModuleListByIds(",1,2,",null);
			operList = this.getModuleService().getOperationList();
		} else {
			try {
				VcomSysRole role = this.getRoleService().query(this.getRoleobj().getId());
				if (role.getPermissions() == null|| role.getPermissions().equals("")) {
					modellist = this.getModuleService().getModuleList();
					operList = this.getModuleService().getOperationList();
				} else {
					modellist = this.getModuleService().getModuleListByIds(GetPermission.getPer(role.getPermissions(), false,true), false);
					operList = this.getModuleService().getOperationListByIds(GetPermission.getPer(role.getPermissions(), false,false), false);
					// 对数据的补救处理
					Map map = new HashMap();
					String ids = role.getPermissions() + ",";
					// 对菜单的补救
					for (VcomSysModule model : modellist) {
						map.put(model.getId(), "");
						String parentid = model.getParentid();
						while (!parentid.equals("-1")&& map.get(parentid) == null) {
							ids = ids.replaceAll("mc?_" + parentid + ",", "");
							VcomSysModule _model = this.getModuleService().getModuleObjById(parentid);
							parentid = _model.getParentid();
						}
					}
					// 对操作的补救
					for (VcomSysOperation oper : operList) {
						String parentid = oper.getModuleid();
						while (!parentid.equals("-1")&& map.get(parentid) == null) {
							ids = ids.replaceAll("mc?_" + parentid + ",", "");
							VcomSysModule _model = this.getModuleService().getModuleObjById(parentid);
							parentid = _model.getParentid();
						}
					}
					if (!ids.replaceAll(",$", "").equals(role.getPermissions())) {
						modellist = this.getModuleService().getModuleListByIds(GetPermission.getPer(ids, false, true), false);
						operList = this.getModuleService().getOperationListByIds(GetPermission.getPer(ids, false, false),false);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.setLeftTree(CreatePermissionTree
				.createTree(modellist, operList, 1));
		return SUCCESS;
	}

	/**
	 * 权限分配已分配权限
	 * 
	 * @return
	 */
	public String getPermissionTree() {
		List modellist = new ArrayList();
		List operList = new ArrayList();
		if (this.getRoleobj().getId() != null) {
			try {
				VcomSysRole role = this.getRoleService().query(this.getRoleobj().getId());
				if (role.getPermissions() != null&& !role.getPermissions().equals("")) {
					modellist = this.getModuleService().getModuleListByIds(GetPermission.getPer(role.getPermissions(), true,true), true);
					operList = this.getModuleService().getOperationListByIds(GetPermission.getPer(role.getPermissions(), true,false), true);
				}
			} catch (ObjectNotFindException e) {
				e.printStackTrace();
			}

		}
		this.setRightTree(CreatePermissionTree.createTree(modellist, operList,
				2));
		return SUCCESS;
	}

	/**
	 * 保持角色权限信息
	 * 
	 * @return
	 */
	public String saveOrUpdatePermissions() {
		try {
			VcomSysRole role = this.getRoleService().query(this.getRoleobj().getId());
			String pertree = this.getRightTree().replaceAll("other", "") + ",";
			for (String s : pertree.split(",")) {
				if (this.getLeftTree().indexOf(s + ",") >= 0) {
					pertree = pertree.replaceAll(s + ",", s.replace("m_", "mc_")+ ",");
				}
			}
			role.setPermissions(pertree.replaceAll(",$", ""));
			this.getRoleService().saveOrupdate(role);
			LogUtil.insert_log("分配角色权限成功!", request);
		} catch (Exception e) {
			this.setBackvalue(e.getMessage());
			e.printStackTrace();
			LogUtil.insert_log(e.getMessage(), request);
		}
		return SUCCESS;
	}

	public VcomSysRole getModel() {
		return this.getRoleobj();
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
