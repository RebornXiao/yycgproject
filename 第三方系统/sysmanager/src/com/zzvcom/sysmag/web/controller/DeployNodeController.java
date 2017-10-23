package com.zzvcom.sysmag.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.packer.DeployNodeTree;
import com.zzvcom.sysmag.pojo.DeployNode;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.DeployNodeService;
import com.zzvcom.sysmag.service.ModuleService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.DeployNodeValidator;
import com.zzvcom.sysmag.validation.Errors;
import com.zzvcom.sysmag.validation.MessageInfo;
@Controller
public class DeployNodeController {
	@Autowired
	private DeployNodeService deployNodeService;
	@Autowired AuthorityService authService;
	@Autowired ModuleService moduleService;
	@Autowired 
	private SysLogService sysLogService;
	@Autowired
	private DeployNodeValidator deployNodeValidator;
	
	
	@RequestMapping("/deployNode/show.do")
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("DeployNodeMgmt");
		//mav.addObject("toolbar", authService.loadOperationToolBar(oid));
		return mav;
	}
	
	@RequestMapping("/deployNode/list.do")
	public ModelAndView listDeployNodeService(
			@RequestParam int start, 
			@RequestParam int limit,
			@RequestParam String sysId) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			PagingResultDTO result = deployNodeService.loadDeployNodeList(start, limit, sysId);
			mav.addObject("total", result.getTotalCount());
			mav.addObject("rows", result.getResultList());
		} catch (DataAccessException e) {
			mav.addObject("success", false);
		}
		return mav;
	}
	
	@RequestMapping("/deployNode/tree.do")
	public ModelAndView TreeDeployNodeService() {
		List<SubSystem> result = moduleService.loadSystemList(new Role());
		
		ModelAndView mav = new ModelAndView("jsonTreeView");
		mav.addObject("tree", DeployNodeTree.pack(result));
		mav.addObject("excluded", "checked,children");
		return mav;
	}
	
	@RequestMapping("/deployNode/save.do")
	public ModelAndView saveDeployNode(User loginUser,HttpServletRequest request,DeployNode deployNode) {
		ModelAndView mav =  new ModelAndView("jsonView");
		
		//校验表单
		Errors errors = deployNodeValidator.validate(deployNode);
		
		if(errors.hasErrors()) {
			mav.addObject("success", false);
			mav.addObject("errors",errors);
			return mav;
		}
		try {
			deployNodeService.saveDeployNode(deployNode);
			sysLogService.insertLog(loginUser, request, MessageInfo.DEPLOYNODEMGMT, deployNode.getNodeId(), deployNode.getNodeName());
			mav.addObject("success",true);
			mav.addObject("status", true);
			mav.addObject("msg",MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (DataAccessException e) {
			mav.addObject("success", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}
	
	@RequestMapping("/deployNode/delete.do")
	public ModelAndView deleteDeployNode(User loginUser,HttpServletRequest request,@RequestParam("nodeId") String nodeId) {
		ModelAndView mav =  new ModelAndView("jsonView");
		try {
			String nodeName=deployNodeService.getDeployNodeNameById(nodeId).getNodeName();
			deployNodeService.removeDeployNode(nodeId);
			sysLogService.insertLog(loginUser, request, MessageInfo.DEPLOYNODEMGMT,MessageInfo.DEL, nodeName);
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
