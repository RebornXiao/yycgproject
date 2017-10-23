package zzvcom.sys.service;

import java.util.List;

import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.util.PageUtil;

public interface UserService
{
	/**
	 * 新增用户接口
	 * @param user
	 * @throws Exception
	 */
	public String saveUser(VcomSysUser user) throws Exception;
	/**
	 * 修改用户接口
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(VcomSysUser user) throws Exception;

	/**
	 * 删除用户接口
	 * @param user
	 * @throws Exception
	 */
	public void deleteUser(VcomSysUser user) throws Exception;
	/**
	 * 根据ID获取特定用户接口
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public VcomSysUser query(String id) throws Exception;
	/**
	 * 根据分页获取用户信息
	 * @param page
	 * @return
	 */
	public List getAllBySplitPage(PageUtil page,VcomSysUser user);
	
	public void deleteUsers(String ids)throws Exception;
    public void updateInitPWDByUsers(String ids)throws Exception;
	public boolean checkUserCode(String usercode);
	public List<VcomSysUser> getUserList();
	public List<VcomSysUser> queryByUser(VcomSysUser user);
	public  List<VcomSysUser> queryByUsercode(VcomSysUser user);
	public String getInitPWD()throws Exception;
}
