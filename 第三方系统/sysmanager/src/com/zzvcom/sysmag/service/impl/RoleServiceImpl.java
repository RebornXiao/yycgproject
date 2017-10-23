package com.zzvcom.sysmag.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.persistence.dao.AreaDao;
import com.zzvcom.sysmag.persistence.dao.AuthorityDao;
import com.zzvcom.sysmag.persistence.dao.RoleDao;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authDao;
	@Autowired
	private AreaDao areaDao;

	public Role getRole(String roleId) {
		Role role = roleDao.getRoleById(roleId);
		return role;
	}

	@Transactional(readOnly = true)
	public PagingResultDTO loadRoleList(User user, int start, int limit) {
		PagingResultDTO result = roleDao.getRoleList(user.getUserName(), start, limit);
		injectRoleNexusInfo(user,result);
		
		return result;
	}

	private void injectRoleNexusInfo(User loginUser, PagingResultDTO result) {
		List<Role> roleList = result.getResultList();
		roleList.remove(loginUser.getRole());
		for(Role role : roleList) {
			if(null == (authDao.getAuthSystemIds(role.getRoleId())) || (authDao.getAuthSystemIds(role.getRoleId())).isEmpty()) {
				role.setIsAssigned("0");
			}
			else {
				role.setIsAssigned("1");
			}
		}
		result.setResultList(roleList);
	}
	
	@Transactional
	public void removeRole(String roleId) {
		if (isRoleUsing(roleId)) {
			throw new IllegalDeleteException(new Role("roleId"));
		}
		removeRoleAuth(roleId);
		roleDao.deleteRole(roleId);
	}
	
	public boolean isRoleUsing(String roleId) {
		return !roleDao.getUserUsingRoleList(roleId).isEmpty();
	}
	
	private void removeRoleAuth(String roleId) {
		List<String> systemIdList = authDao.getAuthSystemIds(roleId);
		if (!systemIdList.isEmpty()) {
			for (String systemId : systemIdList) {
				String rsId = authDao.getAuthSystemId(roleId, systemId);
				List<String> dnIdList = authDao.getAuthDeployNodeIds(roleId, systemId);
				if (!dnIdList.isEmpty()) {
					for (String deployNodeId : dnIdList) {
						String rnId = authDao.getAuthDeployNodeId(rsId, deployNodeId);
						List<String> moduleIdList = authDao.getAuthModuleIds(roleId, systemId, deployNodeId);
						if (!moduleIdList.isEmpty()) {
							for (String moduleId : moduleIdList) {
								String rmId = authDao.getAuthModuleId(rnId, moduleId);
								authDao.deleteOperationAuth(rmId, null);
							}
						}
						authDao.deleteModuleAuth(rnId, null);
					}
				}
				authDao.deleteDeployNodeAuth(rsId, null);
			}
		}
		authDao.deleteSystemAuth(roleId,null);
	}

	@Transactional
	public void saveRole(Role role) {
		if (role.getRoleId().equals("") || role.getRoleId() == null) {
			roleDao.insertRole(role);
		} else {
			roleDao.updateRole(role);
		}
	}

	public Role getRoleById(String roleId) {
		return roleDao.getRoleById(roleId);
	}

}
