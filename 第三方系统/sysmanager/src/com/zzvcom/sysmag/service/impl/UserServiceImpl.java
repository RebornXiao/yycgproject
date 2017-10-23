package com.zzvcom.sysmag.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.exception.IllegalAuthPasswordException;
import com.zzvcom.sysmag.persistence.dao.AreaDao;
import com.zzvcom.sysmag.persistence.dao.AuthorityDao;
import com.zzvcom.sysmag.persistence.dao.BusinessDao;
import com.zzvcom.sysmag.persistence.dao.RoleDao;
import com.zzvcom.sysmag.persistence.dao.SubSystemDao;
import com.zzvcom.sysmag.persistence.dao.TaskRecorder;
import com.zzvcom.sysmag.persistence.dao.UserDao;
import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.Task;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.UserService;
import com.zzvcom.sysmag.util.PublicTools;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private AuthorityDao authDao;
	@Autowired
	private SubSystemDao sysDao;
	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private TaskRecorder recorder;

	public List<Area> loadRoleControllAreaList(Role role, String areaId) {
		if (areaId.equals("root")) {
			List<Area> areaList = new ArrayList<Area>();
			areaList.add(areaDao.getRoleArea(role.getRoleId()));
			return areaList;
		}
		return areaDao.getLowerAreaList(areaId);

	}
	public List<Area> loadUserControllAreaList(User user,String areaId) {
		if (areaId.equals("root")) {
			List<Area> areaList = new ArrayList<Area>(); 
			if(user.isAdmin()){
				areaList = areaDao.getUserDirectAreaList();
			}else{
				areaList = userDao.getUserAreaList(user.getUserName());
			}
			
			return areaList;
		}
		return areaDao.getLowerAreaList(areaId);

	}

	@Transactional(readOnly = true)
	public List<Business> loadUserBusinessList(User user, boolean direct) {
		
		//System.out.println("user isadmin-=="+user.getRole().getRoleId());
		if (user.isAdmin()) {
			System.out.println("user isadmin");
			return businessDao.getAllBusinessList();
		} else {
			if (direct) {
				return businessDao.getDirectBusinessList(user.getUserName());
			} else {
				return businessDao.getBusinessList(user.getUserName());
			}

		}
	}

	@Transactional(readOnly = true)
	public PagingResultDTO<User> loadUserList(User loginUser, int start, int limit) {
		if (loginUser.isAdmin()) {
			PagingResultDTO<User> result = userDao.getUserList(start, limit);
			injectNexusInfo(loginUser, result);
			return result;
		}
		PagingResultDTO<User> result = userDao.getUserList(loginUser.getUserName(), start, limit);
		injectNexusInfo(loginUser, result);
		return result;
	}

	/**
	 * 加入关联信息，包括用户区域，用户业务，用户角色等。
	 * 
	 * @param loginUser
	 * @param result
	 *            已查询出的用户信息结果
	 */
	@SuppressWarnings("unchecked")
	private void injectNexusInfo(User loginUser, PagingResultDTO result) {
		List<User> userList = result.getResultList();
		for (User resultUser : userList) {
			resultUser.setAreaList(areaDao.getUserDirectAreaList(resultUser
					.getUserName()));
			resultUser.setBusinessList(businessDao.getDirectBusinessList(resultUser
					.getUserName()));
			resultUser.setRole(roleDao.getUserRole(resultUser.getUserName()));
		}
		// 去除当前登录用户
		userList.remove(loginUser);

		result.setResultList(userList);
	}

	@Transactional
	public void removeUser(User loginUser, String userName) {
		// 获取用户所拥有的部署节点
		List<String> nodeList = getUserAuthNodeList(userName);

		userDao.deleteUserArea(userName);
		userDao.deleteUserBusiness(userName);
		userDao.deleteUserRole(userName);
		userDao.deleteUser(userName);

		// 记录数据同步任务
		recorder.record(loginUser, new User(userName), Task.OPT_TYPE_DEL,
				nodeList);

	}

	@Transactional
	public void saveUser(User loginUser, User user, String cmd) {
		user.setLastUpdate(PublicTools.getCurtimeOfString("yyyyMMddHHmmss"));

		if (!user.getPassword().equals("")) {
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		}

		if (cmd.equals("add")) { /* 添加用户 */
			user
					.setCreateTime(PublicTools
							.getCurtimeOfString("yyyyMMddHHmmss"));
			userDao.insertUser(user);
			userDao
					.saveUserRole(user.getUserName(), user.getRole()
							.getRoleId());

			/* 保存用户的区域信息 */
			for (Area area : user.getAreaList()) {
				userDao.saveUserArea(user.getUserName(), area.getAreaId());
			}

			/* 保存用户的业务信息 */
			for (Business business : user.getBusinessList()) {
				userDao.saveUserBusiness(user.getUserName(), business
						.getBusinessId());
			}

			// 记录数据同步任务
			// recorder.record(loginUser, user, Task.OPT_TYPE_ADD);
			List<String> authNodeList = getUserAuthNodeList(user.getUserName());
			recorder.record(loginUser, user, Task.OPT_TYPE_ADD, authNodeList);

		} else { /* 修改用户 */

			// 记录用户的原有节点权限
			List<String> oldNodeList = getUserAuthNodeList(user.getUserName());

			userDao.updateUser(user);

			userDao.deleteUserRole(user.getUserName());
			userDao
					.saveUserRole(user.getUserName(), user.getRole()
							.getRoleId());

			userDao.deleteUserArea(user.getUserName());
			for (Area area : user.getAreaList()) {
				userDao.saveUserArea(user.getUserName(), area.getAreaId());
			}

			userDao.deleteUserBusiness(user.getUserName());
			for (Business business : user.getBusinessList()) {
				userDao.saveUserBusiness(user.getUserName(), business
						.getBusinessId());
			}

			// 读取用户更新后的新的节点权限
			List<String> newNodeList = getUserAuthNodeList(user.getUserName());

			// 记录数据同步任务
			recorder.record(loginUser, user, oldNodeList, newNodeList);
		}

	}

	public List<Role> getAddOrEditRoleList(User user, String cmd, String edituser) {
		return roleDao.getAddOrEditRoleList(user.getUserName(), cmd, edituser);
	}

	public void changePassword(User loginUser, String password_old) {
		User _user = userDao.getUser(loginUser.getUserName());
		if (!_user.getPassword().equals(DigestUtils.md5Hex(password_old))) {
			throw new IllegalAuthPasswordException("User password invalid.");
		}
		_user.setPassword(DigestUtils.md5Hex(loginUser.getPassword()));
		userDao.updateUser(_user);

		List<String> authNodeList = getUserAuthNodeList(_user.getUserName());
		// 记录数据同步任务
		recorder.record(loginUser, _user, Task.OPT_TYPE_EDIT, authNodeList);
	}

	private List<String> getUserAuthNodeList(String userName) {
		Role role = roleDao.getUserRole(userName);

		List<String> nodeList = new ArrayList<String>();
		List<SubSystem> systemList = sysDao.getSystemList(role.getRoleId());

		for (SubSystem subSystem : systemList) {
			for (String node : authDao.getAuthDeployNodeIds(role.getRoleId(),
					subSystem.getSystemId())) {
				nodeList.add(node);
			}
		}
		return nodeList;
	}

}
