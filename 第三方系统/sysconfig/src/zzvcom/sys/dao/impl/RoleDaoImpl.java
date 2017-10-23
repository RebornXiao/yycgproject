package zzvcom.sys.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import zzvcom.sys.dao.RoleDao;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysRole;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.util.PageUtil;

/**
 * 用户数据访问实现类
 * 
 * @author lzq.
 * 
 */
public class RoleDaoImpl  extends SqlMapClientDaoSupport  implements
		RoleDao {
	public static final Logger log = Logger.getLogger(RoleDaoImpl.class);


	/**
	 * 根据分页获取用户信息
	 * 
	 * @param page
	 * @return
	 */
	public List getAllBySplitPage(PageUtil page, VcomSysRole role) {
		Map map = new HashMap();
		map.put("rolename", role.getRolename() == null ? "" : role.getRolename());
        map.put("owner", role.getOwner() == null ? "" : role.getOwner());
        map.put("extstr", role.getExtstr() == null ? "" : role.getExtstr());
		int count=(Integer) getSqlMapClientTemplate().queryForObject("vcomrole.getAllBySplitPageCount", map);
		page.setTotalRow(count);
		map.put("start", page.getStart());
		map.put("end", page.getEnd());
		map.put("rowend", page.getRowend());
		return this.getSqlMapClientTemplate().queryForList("vcomrole.getAllBySplitPage",map);
	}

	/**
	 * 根据角色id获取集合
	 * 
	 * @param ids
	 * @return
	 */
	public List getRoleByIds(String ids) {
		return this.getSqlMapClientTemplate().queryForList("vcomrole.getRoleByIds",ids);
	}

	/**
	 * 获取所有的角色
	 * 
	 * @return
	 */
	public List getAllRole() {
		return this.getSqlMapClientTemplate().queryForList("vcomrole.getAllRole");
	}
	/**
	 * 验证名称是否重复
	 */
	public boolean isContainRolename(String rolename, long exceptid) {
		Map map=new HashMap();
		map.put("rolename", rolename);
		if(exceptid > 0)map.put("id", String.valueOf(exceptid));
		List list=this.getSqlMapClientTemplate().queryForList("vcomrole.isContainRolename",map);
		if (null == list || list.isEmpty())
			return false;
		else
			return true;
	}

	public boolean isContainRolename(String rolename) {

		return isContainRolename(rolename, -1l);
	}

	public String create(VcomSysRole entity) throws SaveOrUpdateException {
		return (String) getSqlMapClientTemplate().insert("vcomrole.create",entity);
	}

	public void delete(VcomSysRole entity) throws DeleteException {
		getSqlMapClientTemplate().delete("vcomrole.delete", entity);
	}

	public VcomSysRole query(String id) throws ObjectNotFindException {
		return (VcomSysRole) this.getSqlMapClientTemplate().queryForObject("vcomrole.query", id);
	}

	public void saveOrupdate(VcomSysRole entity) throws SaveOrUpdateException {
		if(entity.getId()==null)
			getSqlMapClientTemplate().insert("vcomrole.create",entity);
		else
			getSqlMapClientTemplate().insert("vcomrole.update",entity);
	}

    public boolean isUsedRole(String roleid)
    {
        int count = (Integer)this.getSqlMapClientTemplate().queryForObject("vcomrole.findroleuserCount", roleid);
        if(count > 0){
            return true;
        }
        return false;
    }
}
