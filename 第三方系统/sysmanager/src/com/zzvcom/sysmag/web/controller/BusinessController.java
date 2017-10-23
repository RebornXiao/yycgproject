package com.zzvcom.sysmag.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.BusinessService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.BusinessValidator;
import com.zzvcom.sysmag.validation.Errors;
import com.zzvcom.sysmag.validation.MessageInfo;

/**
 * 业务管理控制器
 * 负责业务的增加、修改、删除
 * @author Wang Xiaoming
 */

@Controller
public class BusinessController {
	@Autowired
	private BusinessService businessService;
	@Autowired
	private AuthorityService authService;
	@Autowired
	private BusinessValidator businessValidator;
	@Autowired 
	private SysLogService sysLogService;
	@RequestMapping("/business/show.do")
	public ModelAndView show(@RequestParam String oid) {
		ModelAndView mav = new ModelAndView("BusinessMgmt");
		mav.addObject("toolbar", authService.loadOperationToolBar(oid));
		return mav;
	}	
	
	@RequestMapping("/business/list.do")
	public ModelAndView listBusiness(@RequestParam int start, @RequestParam int limit) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			PagingResultDTO<Business> result = businessService.loadBusinessList(start, limit);
			mav.addObject("total", result.getTotalCount());
			mav.addObject("rows", result.getResultList());
		} catch (DataAccessException e) {
			mav.addObject("success", false);
		}
		return mav;
	}
	
	@RequestMapping("/business/save.do")
	public ModelAndView saveBusiness(User loginUser,HttpServletRequest request, Business business) {
		ModelAndView mav =  new ModelAndView("jsonView");
		
		//校验表单
		Errors errors = businessValidator.validate(business);
		
		if(errors.hasErrors()) {
			mav.addObject("success", false);
			mav.addObject("errors",errors);
			return mav;
		}
		try {
			businessService.saveBusiness(loginUser, business);
			sysLogService.insertLog(loginUser, request, MessageInfo.BUSINESSMGMT, business.getBusinessId(), business.getBusinessName());
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
	
	@RequestMapping("/business/delete.do")
	public ModelAndView deleteBusiness(User loginUser,HttpServletRequest request, @RequestParam("businessId") String businessId) {
		ModelAndView mav =  new ModelAndView("jsonView");
		try {
			String bussName=businessService.loadBusiness(businessId).getBusinessName();
			businessService.removeBusiness(loginUser, businessId);
			sysLogService.insertLog(loginUser, request, MessageInfo.BUSINESSMGMT,MessageInfo.DEL,bussName);
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
