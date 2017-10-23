package zzvcom.sys.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import zzvcom.sys.dao.UserDao;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.util.PageUtil;

/**
 * 用户数据访问实现类
 * 
 * @author LHQ.
 * 
 */
public class UserDaoImpl extends SqlMapClientDaoSupport implements UserDao {

	public static final Logger log = Logger.getLogger(UserDaoImpl.class);
	/**
	 * 根据分页获取用户信息
	 * 
	 * @param page
	 * @return
	 */
	public List<VcomSysUser> getAllBySplitPage(PageUtil page, VcomSysUser user) {
		Map map = new HashMap();
		map.put("username", user.getUsername()==null?"":user.getUsername());
        map.put("usercode", user.getUsercode()==null?"":user.getUsercode());
        map.put("role", user.getRole()==null?"":user.getRole());
        map.put("groupid", user.getGroupid()==null?"":user.getGroupid());
        map.put("sysid", user.getSysid()==null?"":user.getSysid());
        map.put("isstart", user.getIsstart()==null?"":user.getIsstart());
		int count=(Integer) getSqlMapClientTemplate().queryForObject("vcomuser.queryUserBySplitPageCount", map);
		page.setTotalRow(count);
		map.put("start", page.getStart());
		map.put("end", page.getEnd());
		map.put("rowend", page.getRowend());
		return this.getSqlMapClientTemplate().queryForList("vcomuser.queryUserBySplitPage",map);

	}
	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	public List<VcomSysUser> getUserList() {
		Map map = new HashMap();
		map.put("username", "");
		return this.getSqlMapClientTemplate().queryForList("vcomuser.getAllBySplitPage",map);
	}
	
	public List<VcomSysUser> queryByCode(String usercode){
		return this.getSqlMapClientTemplate().queryForList("vcomuser.queryByCode",usercode);
	}
	/**
	 * 根据用户名和密码查询用户
	 */
	public List<VcomSysUser> queryByUser(VcomSysUser user){
		Map map=new HashMap();
		map.put("usercode", user.getUsercode());
		map.put("password", user.getPassword());
		return this.getSqlMapClientTemplate().queryForList("vcomuser.queryByUser",map);
	}
	/**
	 * 保存用户信息
	 */
	public String create(VcomSysUser entity) throws SaveOrUpdateException {
		return (String) getSqlMapClientTemplate().insert("vcomuser.create",entity);
	}
	/**
	 * 删除用户信息
	 */
	public void delete(VcomSysUser entity) throws DeleteException {
		getSqlMapClientTemplate().delete("vcomuser.delete", entity);
	}
	/**
	 * 根据id检索用户
	 */
	public VcomSysUser query(String id) throws ObjectNotFindException {
		return (VcomSysUser) this.getSqlMapClientTemplate().queryForObject("vcomuser.query", id);
	}
	/**
	 * 保存或者修改用户信息
	 */
	public void saveOrupdate(VcomSysUser entity) throws SaveOrUpdateException {
		if(entity.getId()==null)
			getSqlMapClientTemplate().insert("vcomuser.create",entity);
		else
			getSqlMapClientTemplate().insert("vcomuser.update",entity);
	}

}
