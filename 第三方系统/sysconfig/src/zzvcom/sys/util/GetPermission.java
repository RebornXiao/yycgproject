package zzvcom.sys.util;

import java.util.ArrayList;
import java.util.List;

import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.ModuleService;
import zzvcom.sys.service.RoleService;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
/**
 * 获取用户权限信息
 * @author liuzhiqiang
 *
 */
public class GetPermission {
	/**
	 * 获取登录用户权限
	 * @param user 当前登录用户
	 * @return 权限串，用逗号分隔
	 */
	public static String getUserPermission(VcomSysUser user){
		return getPer(user.getPermissions(),true,true);
	}
	/**
	 * 返回登录的权限树
	 * @param user
	 * @param modelService
	 * @return
	 */
	public static String getLoginUserPermission(VcomSysUser user,ModuleService modelService,RoleService roleService){
		List modellist=null;
		if(user.getUsercode().equals("admin")){
			modellist=modelService.getModuleList();
		}else{
			String ids=user.getPermissions();
			
			/*List<VcomSysRole> rolelist=roleService.getRoleByIds(user.getRole());
			
			//per=user.getPermissions();
			for (VcomSysRole us : rolelist) {//组合角色权限
				ids=ids+us.getPermissions()+",";
			}*/
			/*for(String s:user.getPermissions().split(",")){//匹配掉已经删除的权限
				if(ids.replace("c", "").indexOf(s.replace("c", ""))<0){
					per=per.replaceAll("mc?"+s+",", "").replaceAll(s+",", "");
				}
			}*/
			//user.setPermissions(per);
			modellist=modelService.getModuleListByIds(getPer(ids,true,true), true);
		}
//		for(int i=modellist.size()-1;i>=0;i--){
//			VcomSysModule firstmodel=(VcomSysModule) modellist.get(i);
//			if(firstmodel.getParentid().intValue()!=-1){
//				for(int j=modellist.size()-1;j>=0;j--){
//					VcomSysModule nextmodel=(VcomSysModule) modellist.get(j);
//					if(firstmodel.getParentid().equals(nextmodel.getId())){
//						TreeForm tree=new TreeForm();
//						tree.setId("o_"+firstmodel.getId());
//						tree.setText(firstmodel.getModulename());
//						if(firstmodel.getLink()!=null&&!firstmodel.getLink().equals(""))
//							tree.setHref(firstmodel.getLink().indexOf("?")>=0?(firstmodel.getLink()+"&mid="+firstmodel.getId()):(firstmodel.getLink()+"?mid="+firstmodel.getId()));
//						if(firstmodel.getChildren().size()==0)
//							tree.setLeaf(true);
//						else {
//							tree.setLeaf(false);
//							List _l=nextmodel.getChildren();
//							for(int m=_l.size()-1;m>=0;m--){
//								tree.getChildren().add(_l.get(m));
//							}
//						}
//						nextmodel.getChildren().add(tree);
//						modellist.remove(i);
//						break;
//					}
//				}
//			}else{
//				TreeForm tree=new TreeForm();
//				tree.setId("o_"+firstmodel.getId());
//				tree.setText(firstmodel.getModulename());
//				if(firstmodel.getLink()!=null)
//					tree.setHref(firstmodel.getLink().indexOf("?")>=0?(firstmodel.getLink()+"&mid="+firstmodel.getId()):(firstmodel.getLink()+"?mid="+firstmodel.getId()));
//				if(firstmodel.getChildren().size()==0)
//					tree.setLeaf(true);
//				else {
//					tree.setLeaf(false);
//					List _l=firstmodel.getChildren();
//					for(int m=_l.size()-1;m>=0;m--){
//						tree.getChildren().add(_l.get(m));
//					}
//				}
//				modellist.set(i, tree);
//			}
//		}
		
		for(int i=modellist.size()-1;i>=0;i--){
			VcomSysModule firstmodel=(VcomSysModule) modellist.get(i);
			
			if(!firstmodel.getParentid().equals("-1")){
				for(int j=modellist.size()-1;j>=0;j--){
					VcomSysModule nextmodel=(VcomSysModule) modellist.get(j);
					if(firstmodel.getParentid().equals(nextmodel.getId())){
						TreeForm tree=new TreeForm();
						tree.setId(firstmodel.getId().toString());
						tree.setText(firstmodel.getModulename());
						tree.setIcon("../images/icon-1.png");
						tree.setParentid(firstmodel.getParentid());
						//tree.setChecked(false);
						if(firstmodel.getLink()!=null&&!firstmodel.getLink().equals(""))
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
						
						nextmodel.getChildren().add(tree);
						modellist.remove(i);
						break;
					}
				}
			}else{
				TreeForm tree=new TreeForm();
				tree.setId(firstmodel.getId().toString());
				tree.setText(firstmodel.getModulename());
				tree.setIcon("../images/icon-1.png");
				//tree.setChecked(false);
				
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
				//if(firstmodel.getId().toString().equals(this.getPlatid()))
				modellist.set(i, tree);
			}
		}
		
		
		
		try {
			String value=JSONUtil.serialize(modellist,null,null,false,false);
			return value;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
    /**
     * 返回登录的权限树(只剩系统管理)
     * @param user
     * @param modelService
     * @return
     */
    public static String getLoginUserPermissionExt(VcomSysUser user,ModuleService modelService,RoleService roleService){
        List modellist=null;
        if(user.getUsercode().equals("admin")){
            modellist=modelService.getModuleList();
        }else{
        	 String ids=user.getPermissions();
           
        	 /*List<VcomSysRole> rolelist=roleService.getRoleByIds(user.getRole());
           
            //per=user.getPermissions();
            for (VcomSysRole us : rolelist) {//组合角色权限
                ids=ids+us.getPermissions()+",";
            }*/
            modellist=modelService.getModuleListByIds(getPer(ids,true,true), true);
        }
        
        for(int i=modellist.size()-1;i>=0;i--){
            VcomSysModule firstmodel=(VcomSysModule) modellist.get(i);
            
            if(!firstmodel.getParentid().equals("-1")){
                for(int j=modellist.size()-1;j>=0;j--){
                    VcomSysModule nextmodel=(VcomSysModule) modellist.get(j);
                    if(firstmodel.getParentid().equals(nextmodel.getId())){
                        TreeForm tree=new TreeForm();
                        tree.setId(firstmodel.getId().toString());
                        tree.setText(firstmodel.getModulename());
                        tree.setIcon("../images/icon-1.png");
                        tree.setParentid(firstmodel.getParentid());
                        //tree.setChecked(false);
                        if(firstmodel.getLink()!=null&&!firstmodel.getLink().equals(""))
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
                        
                        nextmodel.getChildren().add(tree);
                        modellist.remove(i);
                        break;
                    }
                }
            }else{
                TreeForm tree=new TreeForm();
                tree.setId(firstmodel.getId().toString());
                tree.setText(firstmodel.getModulename());
                tree.setIcon("../images/icon-1.png");
                //tree.setChecked(false);
                
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
                if(tree.getText().equals("系统管理") || tree.getText().equals("超级管理员特有模块")){
                    
                }else{
                    List<TreeForm> list=tree.getChildren();
                    List ___list=new ArrayList();
                    for (TreeForm treeForm : list) {
                        if(treeForm.getText().equals("系统管理")){
                            ___list.add(treeForm);
                        }
                    }
                    tree.setChildren(___list);
                }
                modellist.set(i, tree);
            }
        }
        
        
        
        try {
            String value=JSONUtil.serialize(modellist,null,null,false,false);
            return value;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * 返回模块对应的操作列表
	 * @return
	 */
	public static String getModelOperations(){
		return null;
	}
	/**
	 * 获取用户权限和操作权限
	 * @param per
	 * @param type
	 * @return
	 */
	public static String getPer(String per,boolean pertype,boolean type){
		if(per.indexOf(",")==0)
			per=per.substring(1);
		per=per+",";
		if(pertype&&type){
			return per.replaceAll("mc?_", "").replaceAll("o_\\d+,?", "").replaceAll(",*$", "");
		}else if(!pertype&&type){
			return per.replaceAll("m_", "").replaceAll("mc_\\d+,?", "").replaceAll("o_\\d+,?", "").replaceAll(",*$", "");
		}else
			return per.replaceAll("o_", "").replaceAll("mc?_\\d+,?", "").replaceAll(",*$", "");
	}
}
