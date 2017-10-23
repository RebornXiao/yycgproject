package com.zzvcom.sysmag.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.packer.ModuleTreePacker;
import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.TreeNode;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.ModuleService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.Errors;
import com.zzvcom.sysmag.validation.MessageInfo;
import com.zzvcom.sysmag.validation.ModuleValidator;

@Controller
public class ModuleController {
	@Autowired 
	private ModuleService moduleService;
	@Autowired AuthorityService authService;
	@Autowired
	private ModuleTreePacker treePacker;
	@Resource(name="moduleValidator")
	private ModuleValidator moduleValidator;
	@Autowired 
	private SysLogService sysLogService;
	@RequestMapping("/module/show.do")
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("ModuleMgmt");
		//System.out.println(authService.loadOperationToolBar(oid));
		//mav.addObject("toolbar", authService.loadOperationToolBar(oid));
		return mav;
	}	
	
	
	@RequestMapping("/module/loadTree.do")
	public ModelAndView loadSystemTree(
			@RequestParam("node") String nodeId, User loginUser) {
		List<TreeNode> treeList = treePacker.pack(moduleService.load(loginUser.getRole(), nodeId));
		
		ModelAndView mav = new ModelAndView("jsonTreeView");
		mav.addObject("tree", treeList);
		mav.addObject("excluded", "checked,children");
		
		return mav;
	}
	
	@RequestMapping("/module/loadList.do")
	public ModelAndView loadModuleList(
			@RequestParam("node") String nodeId, User loginUser) {
		ModelAndView mav = new ModelAndView("jsonView");
		List dataList = (List)moduleService.load(loginUser.getRole(), nodeId).get("data");
		mav.addObject("rows", dataList);
		
		return mav;
	}
	
	@RequestMapping("/module/save.do")
	public ModelAndView saveModule(User loginUser,HttpServletRequest request,@RequestParam("cmd") String cmd, Module module) {
		ModelAndView mav = new ModelAndView("jsonView");
		if(!cmd.equals("add")) {
			moduleValidator.setValidModuleId(false);
		} else {
			moduleValidator.setValidModuleId(true);
		}
		Errors errors = moduleValidator.validate(module);
		
		if (errors.hasErrors()) {
			mav.addObject("success", false);
			mav.addObject("errors", errors);
			return mav;
		}
		try {
			moduleService.saveModule(module, cmd);
			
			sysLogService.insertLog(loginUser, request, MessageInfo.MODULEMGMT, cmd, module.getModuleName());
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
	
	@RequestMapping("/module/delete.do")
	public ModelAndView deleteModule(User loginUser,HttpServletRequest request,@RequestParam("moduleId") String moduleId) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			String moduleName=moduleService.getModuleById(moduleId).getModuleName();
			moduleService.removeModule(moduleId);
			sysLogService.insertLog(loginUser, request, MessageInfo.MODULEMGMT, MessageInfo.DEL, moduleName);
			mav.addObject("success", true);
			mav.addObject("msg", MessageInfo.DELETE_SUCCESS);
			return mav;
		} catch (IllegalDeleteException e) {
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.EXSITS_SUB_MODULE);
			return mav;
		} catch (Exception e) {
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.DELETE_FAILURE);
			return mav;
		}
	}
	
	@RequestMapping("/module/loadSystemList.do")
	public ModelAndView loadSystemList(@RequestParam("roleId") String roleId) {
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("rows", moduleService.loadSystemList(new Role(roleId)));
		return mav;
	}
	
	
	@RequestMapping("/module/saveOrder.do")
	public ModelAndView saveOrder(@RequestParam String ids, @RequestParam String type) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			moduleService.saveOrder(ids, type);
			mav.addObject("success", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (Exception e) {
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
		
	}
}
