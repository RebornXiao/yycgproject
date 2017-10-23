package yycg.base.service;

import java.util.List;

import yycg.base.po.Sysuser;
import yycg.base.po.Usergys;
import yycg.base.po.Userjd;
import yycg.base.po.Useryy;
import yycg.base.vo.ActiveUser;
import yycg.base.vo.Menu;
import yycg.base.vo.Operation;
import yycg.base.vo.SysuserCustom;
import yycg.base.vo.SysuserQueryVo;

/**
 * 用户管理服务接口
 * @author Thinkpad
 *
 */
public interface UserManager {
	
	//根据用户类型和单位id获取单位名称
	public String getSysmcBySysid(String groupid,String sysid) throws Exception;
	/**
	 * 用户登录认证
	 * 用户认方通过返回用户身份信息ActiveUser
	 */
	public ActiveUser userlogin(String userid,String pwd)throws Exception; 
	/**
	 * 根据名称获取监督单位
	 */
	public Userjd getUserjdBymc(String mc) throws Exception;
	public Userjd getUserjdById(String id) throws Exception;
	
	/**
	 * 根据名称获取供货商单位
	 */
	public Usergys getUsergysBymc(String mc) throws Exception;
	public Usergys getUsergysById(String id) throws Exception;
	
	/**
	 * 根据名称获取医院单位
	 */
	public Useryy getUseryyBymc(String mc) throws Exception;
	public Useryy getUseryyById(String id) throws Exception;

	/**
	 * 系统用户查询
	 */
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	public int findSysuserCount(SysuserQueryVo sysuserQueryVo) throws Exception;
	/**
	 * 根据用户账号获取用户信息
	 */
	public Sysuser getSysuserByUserid(String userid) throws Exception;
	
	/**
	 * 根据用户id获取用户信息
	 */
	public Sysuser getSysuserByid(String id) throws Exception;

	/**
	 * 用户添加
	 */
	public void insertSysuser(Sysuser sysuser)throws Exception;
	/**
	 * 用户更新
	 */
	public void updateSysuser(Sysuser sysuser) throws Exception;
	
	/**
	 * 用户删除
	 */
	public void deleteSysuser(String id) throws Exception;
	
	/**
	 * 根据角色id获取菜单
	 */
	public List<Menu> findMenuByroleid(String roleid) throws Exception;
	/**
	 * 根据用户角色获取操作权限
	 */
	public List<Operation> findOperatByRoleid(String roleid) throws Exception;
}
