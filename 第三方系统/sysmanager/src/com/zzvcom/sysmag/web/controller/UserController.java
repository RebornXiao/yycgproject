package com.zzvcom.sysmag.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.exception.IllegalAuthPasswordException;
import com.zzvcom.sysmag.packer.AreaTreePacker;
import com.zzvcom.sysmag.packer.BusinessTreePacker;
import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.TreeNode;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.service.UserService;
import com.zzvcom.sysmag.validation.Errors;
import com.zzvcom.sysmag.validation.MessageInfo;
import com.zzvcom.sysmag.validation.UserValidator;

/**
 * 用户管理控制器
 * 
 * @author Wang Xiaoming
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private SysLogService sysLogService;
	@Resource(name = "userValidator")
	private UserValidator userValidator;

	@RequestMapping("/user/show.do")
	public ModelAndView show(HttpServletRequest request) {
		String operationIds = request.getParameter("oid");
		ModelAndView mav = new ModelAndView("UserMgmt");
		mav
				.addObject("toolbar", authService
						.loadOperationToolBar(operationIds));
		return mav;
	}

	@RequestMapping("/user/list.do")
	public ModelAndView listUser(@RequestParam("start") int start,
			@RequestParam("limit") int limit, User loginUser) {
		ModelAndView mav = new ModelAndView("jsonView");
		PagingResultDTO result = userService.loadUserList(loginUser, start,
				limit);
		mav.addObject("rows", result.getResultList());
		mav.addObject("total", result.getTotalCount());
		return mav;
	}
	
	@RequestMapping("/user/loadRoles.do")
	public ModelAndView listUserRole(User loginUser,
			@RequestParam("cmd") String cmd, 
			@RequestParam("edituser") String edituser) {
		ModelAndView mav = new ModelAndView("jsonView");
		List<Role> roleList = userService.getAddOrEditRoleList(loginUser, cmd, edituser);
		mav.addObject("rows", roleList);
		return mav;
	}

	@RequestMapping("/user/save.do")
	public ModelAndView saveUser(User user, User loginUser,
			HttpServletRequest request,
			@RequestParam("areaIds") String areaIds,
			@RequestParam("businessIds") String businessIds,
			@RequestParam("roleId") String roleId,
			@RequestParam("cmd") String cmd) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			user.setRole(new Role(roleId));
			if (cmd.equals("add")) {
				userValidator.setValidSwitch(true);
			} else {
				userValidator.setValidSwitch(false);
			}

			Errors errors = userValidator.validate(user);
			if (errors.hasErrors()) {
				mav.addObject("errors", errors);
				mav.addObject("success", false);
				return mav;
			}

			user.setAreaList(areaIds);
			user.setBusinessList(businessIds);

			userService.saveUser(loginUser, user, cmd);
			sysLogService.insertLog(loginUser, request, MessageInfo.USERMGMT,
					cmd, user.getUserName());
			mav.addObject("success", true);
			mav.addObject("status", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (Exception e) {
			mav.addObject("succes", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}

	@RequestMapping("/user/delete.do")
	public ModelAndView deleteUser(User loginUser, HttpServletRequest request,
			@RequestParam("userName") String userName) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			userService.removeUser(loginUser, userName);
			sysLogService.insertLog(loginUser, request, MessageInfo.USERMGMT,
					MessageInfo.DEL, userName);
			mav.addObject("success", true);
			mav.addObject("msg", MessageInfo.DELETE_SUCCESS);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.DELETE_FAILURE);
			return mav;
		}
	}

	@RequestMapping("/user/loadRoleAreaTree.do")
	public ModelAndView listRoleArea(User loginUser,@RequestParam("roleId") String roleId,
			@RequestParam("node") String areaId) {
		ModelAndView mav = new ModelAndView("jsonTreeView");
		//List<Area> areaList = userService.loadRoleControllAreaList(new Role(roleId), areaId);
		List<Area> areaList = userService.loadUserControllAreaList(loginUser,areaId);
		List<TreeNode> areaTreeList = AreaTreePacker.packArea(areaList);
		mav.addObject("tree", areaTreeList);
		/*
		 * if (areaId.equals("root")) { mav.addObject("excluded",
		 * "children,checked"); } else {
		 */
		mav.addObject("excluded", "children");
		/* } */
		return mav;
	}

	@RequestMapping("/user/loadBussTree.do")
	public ModelAndView listUserBusiness(
			@RequestParam("userName") String userName, User roleUser) {
		List<Business> controllBussList = userService.loadUserBusinessList(
				roleUser, false);
		List<Business> assignedBussList = userService.loadUserBusinessList(
				new User(userName), true);

		List<TreeNode> bussTreeList = BusinessTreePacker.pack(controllBussList,
				assignedBussList);

		ModelAndView mav = new ModelAndView("jsonTreeView");
		mav.addObject("tree", bussTreeList);
		mav.addObject("excluded", "children");
		return mav;
	}

	@RequestMapping("/user/changePassword.do")
	public ModelAndView changePassword(User loginUser,
			@RequestParam String pwd_new, @RequestParam String pwd_old) {
		ModelAndView mav = new ModelAndView("jsonView");

		try {
			loginUser.setPassword(pwd_new);
			userService.changePassword(loginUser, pwd_old);
			mav.addObject("success", true);
			mav.addObject("status", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (IllegalAuthPasswordException e) {
			mav.addObject("success", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.WRONG_USER_PASSWORD);
			return mav;
		} catch (Exception e) {
			mav.addObject("success", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}

}
