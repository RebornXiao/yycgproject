package com.zzvcom.sysmag.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.TreeNode;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.RoleService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.MessageInfo;


/**
 * 权限分配控制器
 * @author Wang Xiaoming
 */
@Controller
public class AuthorityController {
	@Autowired 
	private RoleService roleService;
	@Autowired private AuthorityService authService;
	@Autowired 
	private SysLogService sysLogService;
/*	@RequestMapping("/auth/loadAuthTree.do")
	public ModelAndView loadAuthTree(
			@RequestParam("systemId") String systemId,
			@RequestParam("roleId") String roleId){
		ModelAndView mav = new ModelAndView("jsonTreeView");
		List<TreeNode> authTree = authService.loadAuthTree(roleId, systemId);
		mav.addObject("tree", authTree);
		mav.addObject("excluded", "expandable");
		return mav;
	}*/
	
	@RequestMapping("/auth/save.do")
	public ModelAndView saveAuth(
			User loginUser,HttpServletRequest request,
			@RequestParam("roleId") String roleId,
			@RequestParam("systemId") String systemId,
			@RequestParam("deployNodeId") String deployNodeId,
			@RequestParam("nodes") String nodeIds) {
		ModelAndView mav = new ModelAndView("jsonView");
		List<String> nodeIdList = new ArrayList<String>();
		if (!nodeIds.equals("")) {
			nodeIdList = Arrays.asList(nodeIds.split(","));
		}
		try {
			authService.saveAuth(roleId, systemId, deployNodeId, nodeIdList);
			
			mav.addObject("success", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			//String roleName=roleService.getRoleById(roleId).getRoleName();
			//sysLogService.insertLog(loginUser, request, MessageInfo.ROLEMGMT, "分配权限", roleName);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}
	
	@RequestMapping("/auth/saveAll.do")
	public ModelAndView saveDeployNodeAllAuth(
			User roleUser,HttpServletRequest request,
			@RequestParam("roleId") String roleId,
			@RequestParam("systemId") String systemId,
			@RequestParam("deployNodeId") String deployNodeId
		) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			authService.saveBatchAuth(roleUser, roleId, systemId, deployNodeId);
			
			mav.addObject("success", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}
	
	@RequestMapping("/auth/loadSystemTree.do")
	public ModelAndView loadSystemNodeList(
			User roleUser,
			@RequestParam String roleId) {
		ModelAndView mav = new ModelAndView("jsonTreeView");
		mav.addObject("tree", authService.loadSystemTree(roleUser, roleId));
		mav.addObject("excluded", "checked");
		return mav;
	}
	
	@RequestMapping("/auth/loadModuleTree.do")
	public ModelAndView loadModuleTree(
			User roleUser,
			@RequestParam String roleId, 
			@RequestParam String systemId,
			@RequestParam String deployNodeId) {
		ModelAndView mav = new ModelAndView("jsonTreeView");
		mav.addObject("tree", authService.loadModuleTree(roleUser, roleId, systemId, deployNodeId));
		return mav;
	}
}
