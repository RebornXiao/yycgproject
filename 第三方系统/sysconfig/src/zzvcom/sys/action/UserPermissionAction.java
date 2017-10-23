package zzvcom.sys.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;

import zzvcom.log.util.LogUtil;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.ModuleService;
import zzvcom.sys.util.CreatePermissionTree;
import zzvcom.sys.util.GetPermission;
import zzvcom.util.PageUtil;

/**
 * 
 * @author lzq.
 * 
 */
public class UserPermissionAction extends BaseAction implements ModelDriven<VcomSysUser>{

	private static final long serialVersionUID = 2327885481039764243L;
	public static final Logger log = Logger.getLogger(UserPermissionAction.class);
	
	/**
	 * 进入用户权限分配页面
	 * @return
	 */
	public String getUserPermissionUI(){
		this.getRoleobj().setUpdatetime(new Date());
		this.setUserList(this.getUserService().getUserList());
		return SUCCESS;
	}
	/**
	 * 权限分配未分配权限
	 * @return
	 */
	public String getAllPermissionTree(){
		List<VcomSysModule> modellist=new ArrayList<VcomSysModule>();
		List<VcomSysOperation> operList=new ArrayList<VcomSysOperation>();
		if(this.getUser().getId()==null){
			modellist=this.getModuleService().getModuleList();
			operList=this.getModuleService().getOperationList();
		}else{
			try {
				VcomSysUser user=this.getUserService().query(this.getUser().getId());
				List<VcomSysRole> rolelist=this.getRoleService().getRoleByIds(user.getRole());
				String ids="";
				for (VcomSysRole us : rolelist) {//组合角色权限
					if(us.getPermissions()!=null)
					ids=ids+us.getPermissions().replaceAll("c", "")+",";
				}
				if(user.getPermissions()!=null&&!user.getPermissions().equals(""))
					for(String s:user.getPermissions().replaceAll("other,", "").split(",")){
						if(ids.indexOf(s+",")>=0){
							ids=ids.replaceAll(s+",", "");
						}
					}
				modellist=this.getModuleService().getModuleListByIds(GetPermission.getPer(ids.equals("")?"-1":ids,true,true), true);
				operList=this.getModuleService().getOperationListByIds(GetPermission.getPer(ids.equals("")?"-1":ids,true,false), true);
				
				//对数据的补救处理
				Map map=new HashMap();
				String _ids=ids;
				//对菜单的补救
				for (VcomSysModule model : modellist) {
					map.put(model.getId(), "");
					String parentid=model.getParentid();
					while(!parentid.equals("-1")&&map.get(parentid)==null){
						_ids=_ids+"m_"+parentid+",";;
						VcomSysModule _model=this.getModuleService().getModuleObjById(parentid);
						parentid=_model.getParentid();
					}
				}
				//对操作的补救
				for (VcomSysOperation oper : operList) {
					String parentid=oper.getModuleid();
					while(!parentid.equals("-1")&&map.get(parentid)==null){
						_ids=_ids+"m_"+parentid+",";;
						VcomSysModule _model=this.getModuleService().getModuleObjById(parentid);
						parentid=_model.getParentid();
					}
				}
				if(!ids.equals(_ids)){
					modellist=this.getModuleService().getModuleListByIds(GetPermission.getPer(ids,false,true), false);
					operList=this.getModuleService().getOperationListByIds(GetPermission.getPer(ids,false,false), false);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.setLeftTree(CreatePermissionTree.createTree(modellist, operList, 1));
		return SUCCESS;
	}
	/**
	 * 权限分配已分配权限
	 * @return
	 */
	public String getPermissionTree(){
		List<VcomSysModule> modellist=new ArrayList<VcomSysModule>(),_modellist=new ArrayList<VcomSysModule>();
		List<VcomSysOperation> operList=new ArrayList<VcomSysOperation>(),_operList=new ArrayList<VcomSysOperation>();
		try {
			if(this.getUser().getId()!=null){
				VcomSysUser user=this.getUserService().query(this.getUser().getId());
				if(user.getPermissions()!=null&&!user.getPermissions().equals("")){
					List<VcomSysRole> rolelist=this.getRoleService().getRoleByIds(user.getRole());
					String ids="",per=user.getPermissions();
					for (VcomSysRole us : rolelist) {//组合角色权限
						ids=ids+us.getPermissions()+",";
					}
					for(String s:user.getPermissions().split(",")){
						if(ids.replace("c", "").indexOf(s.replace("c", ""))<0){
							per=per.replaceAll("mc?"+s+",", "").replaceAll(s+",", "");
						}
					}
					//modellist=this.getModuleService().getModuleListByIds(GetPermission.getPer(per,true,true), true);
					//operList=this.getModuleService().getOperationListByIds(GetPermission.getPer(per,true,false), true);
					modellist=this.getModuleService().getModuleListByIds(GetPermission.getPer(per,true,true), true);
					Map map=new HashMap();
					//补救模块
					for(VcomSysModule modul:modellist){
						
						if(modul.getParentid().equals("-1")){
							map.put(modul.getId(), modul.getId());
							_modellist.add(modul);
						}else{
							if(map.get(modul.getParentid())!=null){
								map.put(modul.getId(), modul.getId());
								_modellist.add(modul);
							}
						}
						
					}
					//补救操作
					operList=this.getModuleService().getOperationListByIds(GetPermission.getPer(per,true,false), true);
					for(VcomSysOperation oper:operList){
						if(map.get(oper.getModuleid())!=null){
							_operList.add(oper);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setRightTree(CreatePermissionTree.createTree(modellist, operList, 2));
		return SUCCESS;
	}
	/**
	 * 保持用户权限信息
	 * @return
	 */
	public String saveOrUpdatePermissions(){
		try {
			VcomSysUser user=this.getUserService().query(this.getUser().getId());
			String pertree=this.getRightTree().replaceAll("other,", "")+",";
			for(String s:pertree.split(",")){
				if(this.getLeftTree().indexOf(s+",")>=0){
					pertree=pertree.replaceAll(s+",", s.replace("m_", "mc_")+",");
				}
			}
			user.setPermissions(pertree.replaceAll(",$", ""));
			this.getUserService().updateUser(user);
			LogUtil.insert_log("保存用户权限成功", request);
		} catch (Exception e) {
			this.setBackvalue(e.getMessage());
			e.printStackTrace();
			LogUtil.insert_log(e.getMessage(), request);
		}
		return SUCCESS;
	}
	public VcomSysUser getModel() {
		return this.getUser();
	}
	
}
