package zzvcom.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import zzvcom.sys.dao.BasicinfoDAO;
import zzvcom.sys.dao.UserDao;
import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
import zzvcom.sys.pojo.Basicinfo;
import zzvcom.sys.pojo.VcomSysUser;
import zzvcom.sys.service.UserService;
import zzvcom.util.MD5;
import zzvcom.util.PageUtil;

public class UserServiceImpl implements UserService
{

	private UserDao userDao;
    
    private BasicinfoDAO basicinfoDAO;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    public BasicinfoDAO getBasicinfoDAO()
    {
        return basicinfoDAO;
    }

    public void setBasicinfoDAO(BasicinfoDAO basicinfoDAO)
    {
        this.basicinfoDAO = basicinfoDAO;
    }

    /**
	 * 新增用户
	 * 
	 * @param user
	 * @throws Exception
	 */
	public String saveUser(VcomSysUser user) throws Exception {
		return (String) userDao.create(user);
	}

	/**
	 * 通用删除对象
	 * 
	 * @param entity
	 * @throws DeleteException
	 */
	public void deleteUser(VcomSysUser user) throws Exception {
		userDao.delete(user);

	}

	public VcomSysUser query(String id) throws Exception {
		return userDao.query(id);
	}

	/**
	 * 通用修改或者保存对象
	 * 
	 * @param entity
	 * @throws SaveOrUpdateException
	 */
	public void updateUser(VcomSysUser user) throws Exception {
		userDao.saveOrupdate(user);

	}

	/**
	 * 根据分页获取用户信息
	 * 
	 * @param page
	 * @return
	 */
	public List getAllBySplitPage(PageUtil page,VcomSysUser user) {
		return userDao.getAllBySplitPage(page, user);
	}

	public void deleteUsers(String ids) throws Exception{
		String[] idValues=ids.split(",");
		for(String id:idValues){
			VcomSysUser user=userDao.query(id);
			userDao.delete(user);
		}
	}
	public boolean checkUserCode(String usercode){
		List<VcomSysUser> list=userDao.queryByCode(usercode);
		if(list==null) list=new ArrayList<VcomSysUser>();
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	public List<VcomSysUser> getUserList(){
		return userDao.getUserList();
	}
	public  List<VcomSysUser> queryByUser(VcomSysUser user){
		return userDao.queryByUser(user);
	}
	public  List<VcomSysUser> queryByUsercode(VcomSysUser user){
		return userDao.queryByCode(user.getUsercode());
	}
	

    public void updateInitPWDByUsers(String ids) throws Exception
    {
        Basicinfo initpwd = basicinfoDAO.selectByPrimaryKey("12001");
        String pwd = initpwd.getValue();
        MD5 md5 = new MD5();
        String md5Code = md5.getMD5ofStr(pwd).trim();
        String[] idValues=ids.split(",");
        for(String id:idValues){
            VcomSysUser user=userDao.query(id);
            user.setPassword(md5Code);
            userDao.saveOrupdate(user);
        }
    }

	public String getInitPWD() throws Exception {
		Basicinfo initpwd = basicinfoDAO.selectByPrimaryKey("12001");
		String pwd = initpwd.getValue();
		return pwd;
	}

}
