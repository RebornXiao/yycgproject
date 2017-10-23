package zzvcom.sys.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import zzvcom.log.util.LogUtil;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.util.MD5;
import zzvcom.util.PageUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends BaseAction implements ModelDriven<VcomSysUser> {

	private static final long serialVersionUID = 200920200001L;
	public static final Logger log = Logger.getLogger(UserAction.class);
	private String searchName;
    private String usercode;
	private String dltTarget;
    private String sysroof;
    private String newuserid;
    public VcomSysUser getModel() {
		return user;
	}

	public String getAllUserLists() {
	    VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession().get("userInfo");
	    if(usr != null && !"admin".equals(usr.getUsercode())){
	        user.setIsstart("0");
	    }
	    if(usr != null && "2".equals(usr.getGroupid())){
	        user.setSysid(usr.getSysid());
        }
		PageUtil page = new PageUtil(this.getPageIndex());
		user.setUsername(searchName!=null?searchName.trim():null);
        user.setUsercode(usercode!=null?usercode.trim():null);
        user.setRole(dltTarget);
        user.setGroupid(sysroof);
		List<VcomSysUser> list = this.getUserService().getAllBySplitPage(page,
				user);
		if(user!=null)user.setGroupid(usr.getGroupid());//查询接口用过后再恢复为当前用户的分组用于页面判断
		this.setUserList(list);
		this.setPageBar(page.getToolsMenu());
        List<VcomSysRole> roles = new ArrayList<VcomSysRole>();
        List<VcomSysRole> resultroles = new ArrayList<VcomSysRole>();
        roles = this.getRoleService().getAllRole();
        String sysid = usr.getSysid();
        for(VcomSysRole role : roles){
            if(!"3".equals(String.valueOf(role.getId())) && !"4".equals(String.valueOf(role.getId()))){
                if(sysid == null || "".equals(sysid)){
                    if("0".equals(role.getOwner())){
                        resultroles.add(role);
                    }
                }else{
                    if(sysid.equals(role.getOwner())){
                        resultroles.add(role);
                    }
                }
            }
        }
        String initpwd = "";
        try{
        	initpwd = this.getUserService().getInitPWD();
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
        request.setAttribute("initpwd", initpwd);
        request.setAttribute("roles", resultroles);
		return SUCCESS;
	}

	public String toAddUserPage() {
	    VcomSysUser curruser = (VcomSysUser) request.getSession().getAttribute("userInfo");
	    String currgroupid = curruser.getGroupid();
		
		request.setAttribute("currgroupid", currgroupid);
		request.setAttribute("usersysid", curruser.getSysid());
		return SUCCESS;
	}

	public String saveUser() {
		boolean flag = this.getUserService().checkUserCode(user.getUsercode());
        if(flag == true){
			this.addActionMessage("登陆账号重复，点击“确定”重新添加，点击“返回”进入用户列表。");
			nexturl = "toAddUserPage.action";
			resulturl= "getAllUserList.action";
			return ERROR;
        }
		if(null!=user.getSysid()&&!"".equals(user.getSysid())){
		    user.setSysid(user.getSysid().trim());
		}
		user.setCreatetime(new Date());
		user.setUpdatetime(new Date());
		MD5 md5 = new MD5();
		user.setPassword(md5.getMD5ofStr(user.getPassword()));
		
		try {
			newuserid = this.getUserService().saveUser(user);
			LogUtil.insert_log("添加用户成功", request);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute("message", "error");
			LogUtil.insert_log("添加用户失败", request);
			e.printStackTrace();
			
		}
		this.addActionMessage("用户添加失败，点击“确定”重新添加，点击“返回”进入用户列表。");
		nexturl = "toAddUserPage.action";
		resulturl= "getAllUserList.action";
		return ERROR;
		// isLog=true;
		// logInfo="添加用户";
		
	}

	public String getUserObj() {
		VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession().get("userInfo");
	    
		try {
			user = this.getUserService().query(user.getId());
			//user.setSysid(usr.getSysid());
			// log.info("sex-----------"+user.isSex());
			List<VcomSysRole> roles = new ArrayList<VcomSysRole>();
			List<VcomSysRole> _roles = new ArrayList<VcomSysRole>();
			List<VcomSysRole> userRoles = new ArrayList<VcomSysRole>();
			
			roles = this.getRoleService().getAllRole();
			
			
			//VcomSysRole obj = this.getRoleService().query(new Long(role));
			//user.setRole(obj.getRolename()); 
			
			
			/*String rolenames = "";
			for (String r : roleValues) {
				VcomSysRole obj = this.getRoleService().query(new Long(r));
				if (obj != null) {
					rolenames += " "+obj.getRolename();
					user.setRole(rolenames);
					userRoles.add(obj);
				}
			}*/
			
			/*
			for(VcomSysRole roless:roles){
				if((role+",").indexOf(String.valueOf(roless.getId()+","))<0){
					_roles.add(roless);
				}
			}
			*/
			//request.setAttribute("roles", _roles);
			//request.setAttribute("userRoles", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
		
	}

	public String updateUser() {
		Date date = new Date();
		user.setUpdatetime(date);
		
		/*
		String[] roles = request.getParameterValues("dltTarget");
		String roleValue = "";
		for (String role : roles) {
			if (roleValue.equals("")) {
				roleValue = role;
			} else {
				roleValue = roleValue + "," + role;
			}
		}
		*/
		try {
			this.getUserService().updateUser(user);
			LogUtil.insert_log("修改用户成功", request);
			this.addActionMessage("用户信息保存成功。");
			resulturl= "getAllUserList.action";
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "error");
			LogUtil.insert_log("用户信息提交失败", request);
		}
		this.addActionMessage("用户信息提交失败，点击返回重新修改。");
		resulturl= "getUserObj.action?user.id="+user.getId();
		return ERROR;
	}

	public void delCheck() {
        VcomSysUser sessionUser = (VcomSysUser)request.getSession().getAttribute("userInfo");
		String values = request.getParameter("ids");
		// log.info("value---------------"+values);
		String[] ids = values.split(",");
		boolean flag = true;
		for (String i : ids) {
			try {
				user = this.getUserService().query(i);
				String usercode = user.getUsercode();
				// log.info("usercode---------------"+usercode);
				/*
				if (usercode.equals("admin")) {
					flag = false;
					break;
				}
				*/
				if(user.getIsstart()!=null && user.getIsstart().equals("1")){
					flag = false;
					break;
				}
                if(sessionUser.getUsercode().equals(user.getUsercode())){
                    flag = false;
                    break;
                }
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			if (flag) {
				response.getWriter().write("true");
			} else {
				response.getWriter().write("false");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String delUser() {
		String ids = request.getParameter("ids");
		String message = "";
		try {
			this.getUserService().deleteUsers(ids);
			message = "删除用户成功";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "error");
			message = "删除用户失败";
			this.addActionMessage("删除用户失败，点击返回用户列表。");
			resulturl= "getAllUserList.action";
			return ERROR;
		}
		LogUtil.insert_log(message, request);
		return SUCCESS;
	}
    
    public String initPWDByUser() {
        String ids = request.getParameter("ids");
        String message = "";
        try {
            this.getUserService().updateInitPWDByUsers(ids);
            message = "初始化用户密码成功";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "error");
            message = "初始化用户密码失败";
        }
        LogUtil.insert_log(message, request);
        return SUCCESS;
    }
    
    /**
	 * 跳转至权限维护
	 * @return
	 * @throws ObjectNotFindException
	 */
	public String getUserPermissions() throws ObjectNotFindException {
		VcomSysUser usr = (VcomSysUser) ActionContext.getContext().getSession().get("userInfo");
	    
		try {
			user = this.getUserService().query(user.getId());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void codeCheck() {
		boolean flag = this.getUserService().checkUserCode(user.getUsercode());
		try {
			if (flag) {
				response.getWriter().write("true");
			} else {
				response.getWriter().write("false");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toView() {
		try {
			user = this.getUserService().query(user.getId());
			String role = user.getRole();
			//String permission = user.getPermissions();
			String[] roleValues = role.split(",");
			String roleValue = "";
			VcomSysRole obj = null;
			for (String r : roleValues) {
				try {
					obj = this.getRoleService().query(r);
					if (obj == null)
						continue;
					if (roleValue.equals("")) {
						roleValue = obj.getRolename();
					} else {
						roleValue = roleValue + "," + obj.getRolename();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			user.setRole(roleValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String toEditPwd() {
		user = (VcomSysUser) request.getSession().getAttribute("userInfo");
		return SUCCESS;
	}

	 

	public String editPwd() {
		MD5 md5 = new MD5();
		String message = "";
		Boolean isSuccess = null;
		VcomSysUser obj = (VcomSysUser) request.getSession().getAttribute(
				"userInfo");
 
		if (obj.getPassword().equals(md5.getMD5ofStr(request.getParameter("oldpassword")))) {
			obj.setPassword(md5.getMD5ofStr(user.getPassword()));
			obj.setUpdatetime(new Date());
			try {
				this.getUserService().updateUser(obj);
				LogUtil.insert_log("修改密码成功", request);
				message = "修改密码成功！";
				isSuccess = true;
			} catch (Exception e) {
				message = "修改密码失败！";
				isSuccess = false;
				LogUtil.insert_log("修改密码失败", request);
				e.printStackTrace();
			}
		} else {
			isSuccess = false;
			message = "原始密码不正确！";
		}

		request.setAttribute("isSuccess", isSuccess);
		request.setAttribute("message", message);
		return SUCCESS;
	}
    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getDltTarget()
    {
        return dltTarget;
    }

    public void setDltTarget(String dltTarget)
    {
        this.dltTarget = dltTarget;
    }

    public String getSysroof()
    {
        return sysroof;
    }

    public void setSysroof(String sysroof)
    {
        this.sysroof = sysroof;
    }

    public String getUsercode()
    {
        return usercode;
    }

    public void setUsercode(String usercode)
    {
        this.usercode = usercode;
    }

	public String getNewuserid() {
		return newuserid;
	}

	public void setNewuserid(String newuserid) {
		this.newuserid = newuserid;
	}

	
}
