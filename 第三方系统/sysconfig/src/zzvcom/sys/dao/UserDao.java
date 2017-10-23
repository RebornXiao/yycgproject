package zzvcom.sys.dao;

import java.util.List;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.util.PageUtil;
/**
 * 用户接口
 * 
 * @author LHQ.
 *
 */
public interface UserDao extends GenericDao<VcomSysUser>
{
	/**
	 * 根据分页获取用户信息
	 * @param page user
	 * @return
	 */
	public List<VcomSysUser> getAllBySplitPage(PageUtil page,VcomSysUser user);
	/**
	 * 获取用户信息列表
	 * @param map
	 * @return
	 */
	public List<VcomSysUser> getUserList();
	
	public List<VcomSysUser> queryByCode(String usercode);
	
	public List<VcomSysUser> queryByUser(VcomSysUser user);
	
}
