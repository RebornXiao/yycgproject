package com.zzvcom.sysmag.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.packer.AreaTreePacker;
import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.TreeNode;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AreaService;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.validation.Errors;
import com.zzvcom.sysmag.validation.MessageInfo;
import com.zzvcom.sysmag.validation.Validator;

@Controller
public class AreaController {
	@Autowired 
	private AreaService areaService;
	@Autowired 
	private SysLogService sysLogService;
	@Autowired private AuthorityService authService;
	
	@Resource(name="areaValidator")
	private Validator areaValidator;
	
	@RequestMapping("/area/show.do")
	public ModelAndView show(
			//,
			//@RequestParam String provinceupload, 
			//@RequestParam String cityidentity,
			//@RequestParam String identitylevel
			) {
		ModelAndView mav = new ModelAndView("AreaMgmt");
		//mav.addObject("toolbar", authService.loadOperationToolBar(oid));
		//mav.addObject("provinceupload", provinceupload);
		//mav.addObject("cityidentity", cityidentity);
		//mav.addObject("identitylevel", identitylevel);
		return mav;
	}
	
	@RequestMapping("/area/list.do")
	public ModelAndView listArea(
			@RequestParam("start") int start,
			@RequestParam("limit") int limit, 
			HttpServletRequest request,
			User roleUser) {
		//String userName = (String)request.getSession().getAttribute("userName");
		String areaId = request.getParameter("srchAreaId");
		ModelAndView mav = new ModelAndView("jsonView");
		PagingResultDTO result = areaService.loadAreaList(start, limit, areaId, roleUser);
		mav.addObject("total", result.getTotalCount());
		mav.addObject("rows", result.getResultList());
		return mav;
	}
	
	@RequestMapping("/area/save.do")
	public ModelAndView saveArea(User loginUser,HttpServletRequest request,Area area) {
		ModelAndView mav = new ModelAndView("jsonView");
		Errors errors = areaValidator.validate(area);
		if(errors.hasErrors()) {
			mav.addObject("success", false);
			mav.addObject("errors", errors);
			return mav;
		}
		try {
			
			String istype=area.getAreaId();
			if(area.getShowOrder()==null || area.getShowOrder().equals("")){
				area.setShowOrder(area.getAreaId());
			}
			areaService.saveArea(loginUser, area);
			
			sysLogService.insertLog(loginUser, request, MessageInfo.AREAMGMT, istype,area.getAreaName());
			mav.addObject("success", true);
			mav.addObject("status", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (DataAccessException e) {
			mav.addObject("success", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			e.printStackTrace();
			return mav;
		}
	}
	
	@RequestMapping("/area/delete.do")
	public ModelAndView deleteArea(User loginUser,HttpServletRequest request, @RequestParam("areaId") String areaId) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			String areaName=areaService.getAreaById(areaId).getAreaName();
			areaService.removeArea(loginUser, areaId);
			
			sysLogService.insertLog(loginUser, request, MessageInfo.AREAMGMT, MessageInfo.DEL, areaName);
			mav.addObject("success", true);
			mav.addObject("msg", MessageInfo.DELETE_SUCCESS);
			return mav;
		} catch (IllegalDeleteException e) {
			mav.addObject("success", false);
			//mav.addObject("msg", MessageInfo.USING);
			mav.addObject("msg", e.getMessage());
			return mav;
		} catch (DataAccessException e) {
			mav.addObject("success", false);
			mav.addObject("msg", MessageInfo.DELETE_FAILURE);
			return mav;
		}
	}
	
	
	@RequestMapping("/area/loadRoleAreaTree.do")
	public ModelAndView loadRoleAreaTree(@RequestParam("node") String areaId, User loginUser) {
		List<Area> areaList = areaService.loadAreaList(areaId, loginUser);
		List<TreeNode> areaTreeList = AreaTreePacker.packArea(areaList);
		ModelAndView mav = new ModelAndView("jsonTreeView");
		mav.addObject("tree", areaTreeList);
		mav.addObject("excluded", "checked,children");
		return mav;
 	}
	
	
	@RequestMapping("/area/loadAreaTree.do")
	public ModelAndView loadAreaTree(@RequestParam("node") String areaId, User roleUser) {
		List<Area> areaList = areaService.loadAreaList(areaId, roleUser);
		List<TreeNode> areaTreeList = AreaTreePacker.packArea(areaList);
		ModelAndView mav = new ModelAndView("jsonTreeView");
		mav.addObject("tree", areaTreeList);
		mav.addObject("excluded", "checked,children");
		return mav;
 	}
	
	@RequestMapping("/area/validAreaChoice.do")
	public ModelAndView validAreaChoice(@RequestParam("areaIds") String areaIds) {
		List<String> areaIdList = Arrays.asList(areaIds.split(","));
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("status", areaService.inSameUpperArea(areaIdList));
		return mav;
	}
}
