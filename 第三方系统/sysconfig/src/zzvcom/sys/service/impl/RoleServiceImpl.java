package zzvcom.sys.service.impl;

import java.util.Date;
import java.util.List;



import zzvcom.sys.dao.RoleDao;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysModule;
import zzvcom.sys.pojo.VcomSysOperation;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.service.RoleService;
import zzvcom.sys.util.ReMsg;
import zzvcom.util.PageUtil;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @throws Exception
	 */
	public String save(VcomSysRole role) throws SaveOrUpdateException {
		return (String) roleDao.create(role);
	}

	/**
	 * 通用删除对象
	 * 
	 * @param entity
	 * @throws DeleteException
	 */
	public void delete(VcomSysRole role) throws DeleteException {
		roleDao.delete(role);

	}

	public VcomSysRole query(String id) throws ObjectNotFindException {
		return roleDao.query(id);
	}

	/**
	 * 通用修改或者保存对象
	 * 
	 * @param role
	 * @throws SaveOrUpdateException
	 */
	public void saveOrupdate(VcomSysRole role) throws SaveOrUpdateException {
		roleDao.saveOrupdate(role);
	}

	/**
	 * 根据分页获取用户信息
	 * 
	 * @param page
	 * @return
	 */
	public List getAllBySplitPage(PageUtil page, VcomSysRole role) {
		return roleDao.getAllBySplitPage(page, role);
	}

	/**
	 * 获取所有的角色
	 * 
	 * @return
	 */
	public List getAllRole() {
		return roleDao.getAllRole();
	}

	/**
	 * 更加角色id获取集合
	 * 
	 * @param ids
	 * @return
	 */
	public List getRoleByIds(String ids) {
		return roleDao.getRoleByIds(ids);
	}

	public ReMsg deleteRole(String id) throws ObjectNotFindException,
			DeleteException {
		VcomSysRole roleObj = roleDao.query(id);
		if (null == roleObj)
			return null;

		// 删除
		roleDao.delete(roleObj);
		return new ReMsg(roleObj.getRolename(), "删除成功！", true);

	}

	public ReMsg saveRole(VcomSysRole roleObj) throws SaveOrUpdateException {
		if (roleDao.isContainRolename(roleObj.getRolename()))
			return new ReMsg(roleObj.getRolename(), "已经存在，无需添加！");

		// 设置时间
		Date now = new Date();
		roleObj.setUpdatetime(now);
		roleObj.setCreatetime(now);
		//
		roleDao.create(roleObj);// 添加
		return new ReMsg(roleObj.getRolename(), "新增成功！", true);

	}

	public ReMsg updateRole(VcomSysRole roleObj) throws SaveOrUpdateException {
		//if (roleDao.isContainRolename(roleObj.getRolename(), roleObj.getId()))
			//return new ReMsg(roleObj.getRolename(), "已经存在，无法修改！");
		//else {
			roleObj.setUpdatetime(new Date());// 设置修改时间
			roleDao.saveOrupdate(roleObj);
			return new ReMsg(roleObj.getRolename(), "修改成功！", true);
		//}

	}
	
	public boolean checkRoleCode(String roleCode)throws ObjectNotFindException {
		VcomSysRole role = roleDao.query(roleCode);
		if(role!=null && role.getIsstart()!=null && role.getIsstart().equals("1")){
			return false;
		}
		return true;
		
	}

    public boolean checkRoleIsUsed(String roleCode) throws ObjectNotFindException
    {
        return roleDao.isUsedRole(roleCode);
    }
}
