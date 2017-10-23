package com.zzvcom.sysmag.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.RoleService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.Errors;
import com.zzvcom.sysmag.validation.MessageInfo;
import com.zzvcom.sysmag.validation.Validator;

@Controller
public class RoleController {
	@Autowired 
	private RoleService roleService;
	@Autowired
	private AuthorityService authService;
	@Autowired 
	private SysLogService sysLogService;
	@Resource(name="roleValidator")
	private Validator roleValidator;
	
	@RequestMapping("/role/show.do")
	public ModelAndView show(){
		ModelAndView mav = new ModelAndView("RoleMgmt");
		//mav.addObject("toolbar", authService.loadOperationToolBar(oid));
		return mav;
	}
	
	
	@RequestMapping("/role/list.do")
	public ModelAndView loadRoleList(
			@RequestParam("start") int start, 
			@RequestParam("limit") int limit,
			User loginUser) {
		ModelAndView mav = new ModelAndView("jsonView");
		PagingResultDTO result = roleService.loadRoleList(loginUser, start, limit);
		mav.addObject("rows", result.getResultList());
		mav.addObject("total", result.getTotalCount());
		return mav;
	}
	
	@RequestMapping("/role/save.do")
	public ModelAndView saveRole(User loginUser,HttpServletRequest request,Role role) {
		ModelAndView mav = new ModelAndView("jsonView");
		role.setCreateUserName(loginUser.getUserName());
		Errors errors = roleValidator.validate(role);
		if (errors.hasErrors()) {
			mav.addObject("errors", errors);
			mav.addObject("success", false);
			return mav;
		}
		try {
			String roleid=role.getRoleId();
			roleService.saveRole(role);
		   sysLogService.insertLog(loginUser, request, MessageInfo.ROLEMGMT, roleid, role.getRoleName());
			mav.addObject("success", true);
			mav.addObject("status", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("success", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}
	
	@RequestMapping("/role/delete.do")
	public ModelAndView deleteRole(User loginUser,HttpServletRequest request,@RequestParam("roleId") String roleId) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			String roleName=roleService.getRoleById(roleId).getRoleName();
			roleService.removeRole(roleId);
			  sysLogService.insertLog(loginUser, request, MessageInfo.ROLEMGMT,MessageInfo.DEL , roleName);
			mav.addObject("success", true);
			mav.addObject("msg", MessageInfo.DELETE_SUCCESS);
			return mav;
		} catch (IllegalDeleteException e) {
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.USING);
			return mav;
		} catch (DataAccessException e) {
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.DELETE_FAILURE);
			return mav;
		}
	}
}
