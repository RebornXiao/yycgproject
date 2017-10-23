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
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.ModuleService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.Errors;
import com.zzvcom.sysmag.validation.MessageInfo;
import com.zzvcom.sysmag.validation.OperationValidator;

@Controller
public class OperationController {
	@Autowired private ModuleService moduleService;
	@Autowired 
	private SysLogService sysLogService;
	@Resource(name="operationValidator") private OperationValidator optValidator;
	
	@RequestMapping("/operation/save.do")
	public ModelAndView saveOperation(User loginUser,HttpServletRequest request, @RequestParam("cmd") String cmd, Operation operation) {
		ModelAndView mav = new ModelAndView("jsonView");
		if (cmd.equals("add")) {
			optValidator.setValidateOptId(true);
		} else {
			optValidator.setValidateOptId(false);
		}
		
		Errors errors = optValidator.validate(operation);
		if (errors.hasErrors()) {
			mav.addObject("success", false);
			mav.addObject("errors", errors);
			return mav;
		}
		
		try {
			moduleService.saveOperation(loginUser, operation, cmd);
			sysLogService.insertLog(loginUser, request, MessageInfo.OPERATEMGMT, cmd, operation.getOperationName());
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
	
	@RequestMapping("/operation/delete.do")
	public ModelAndView deleteOperation(User loginUser,HttpServletRequest request, @RequestParam("operationId") String operationId) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			String operName=moduleService.getOperationById(operationId).getOperationName();
			moduleService.removeOperation(loginUser, operationId);
			sysLogService.insertLog(loginUser, request, MessageInfo.OPERATEMGMT, MessageInfo.DEL, operName);
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
