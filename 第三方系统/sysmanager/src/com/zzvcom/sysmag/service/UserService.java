package com.zzvcom.sysmag.service;

import java.util.List;

import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.User;

/**
 * 用户Service接口
 * @author Wang Xiaoming
 */
public interface UserService {
	public void saveUser(User loginUser, User user, String cmd);
	public void removeUser(User loginUser, String userName);
	public void changePassword(User loginUser, String password_old);
	
	public PagingResultDTO<User> loadUserList(User user, int start, int limit);
	
	public List<Business> loadUserBusinessList(User user, boolean direct);
	public List<Area> loadUserControllAreaList(User user,String areaid);
	public List<Role> getAddOrEditRoleList(User user, String cmd, String edituser);
	
	public List<Area> loadRoleControllAreaList(Role role, String areaId);
}
