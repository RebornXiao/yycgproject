package com.zzvcom.sysmag.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.pojo.WebserviceType;
import com.zzvcom.sysmag.service.AuthorityService;
import com.zzvcom.sysmag.service.SysLogService;
import com.zzvcom.sysmag.service.SysWebserviceCfgService;
import com.zzvcom.sysmag.validation.MessageInfo;

@Controller
public class SysWebserviceCfgController {
	@Autowired
	private SysWebserviceCfgService sysWebserviceCfgService;

	@Autowired
	private AuthorityService authService;
	@Autowired 
	private SysLogService sysLogService;
	@RequestMapping("/syswebservicecfg/show.do")
	public ModelAndView show(@RequestParam
	String oid) {
		ModelAndView mav = new ModelAndView("SysWebserviceMgmt");
		mav.addObject("toolbar", authService.loadOperationToolBar(oid));
		return mav;
	}

	@RequestMapping("/syswebservicecfg/list.do")
	public ModelAndView listSysWsCfg(@RequestParam("start")
	int start, @RequestParam("limit")
	int limit, HttpServletRequest request) {
		String sysname = ServletRequestUtils.getStringParameter(request,
				"sysname", "");
		ModelAndView mav = new ModelAndView("jsonView");
		PagingResultDTO result = sysWebserviceCfgService.loadSysWsCfgList(
				sysname, start, limit);
		mav.addObject("total", result.getTotalCount());
		mav.addObject("rows", result.getResultList());
		return mav;
	}

	@RequestMapping("/syswebservicecfg/listSystem.do")
	public ModelAndView listSystem() {
		ModelAndView mav = new ModelAndView("jsonView");
		List<SubSystem> result = sysWebserviceCfgService.loadSystemList();
		mav.addObject("total", result.size());
		mav.addObject("rows", result);
		return mav;
	}

	@RequestMapping("/syswebservicecfg/listWebserviceType.do")
	public ModelAndView listWebserviceType() {
		ModelAndView mav = new ModelAndView("jsonView");
		List<WebserviceType> result = sysWebserviceCfgService
				.loadWebServiceTypeList();
		mav.addObject("total", result.size());
		mav.addObject("rows", result);
		return mav;
	}

	@RequestMapping("/syswebservicecfg/delete.do")
	public ModelAndView deleteSysWsCfg(User loginUser,HttpServletRequest request,@RequestParam("id")
	String ids) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			String[] ides = ids.split(",");
			for(int i=0;i<ides.length;i++){
				String sysWebName=sysWebserviceCfgService.getNameById(ides[i]).getServicename();
				sysWebserviceCfgService.deleteSysWsCfg(ides[i]);
				sysLogService.insertLog(loginUser, request, MessageInfo.SYSWEBEMGMT, MessageInfo.DEL, sysWebName);
			}
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

	@RequestMapping("/syswebservicecfg/insert.do")
	public ModelAndView insertSysWsCfg(
			User loginUser,HttpServletRequest request,
			@RequestParam("sysid") String sysid, 
			@RequestParam("typeIds") String typeIds, 
			@RequestParam("port") String port, 
			@RequestParam("servicename") String servicename,
			@RequestParam("serviceurn") String serviceUrn,
			@RequestParam("namespace") String namespace) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			sysWebserviceCfgService.insertSysWsCfg(sysid, typeIds, port,servicename, serviceUrn,namespace);
			sysLogService.insertLog(loginUser, request, MessageInfo.SYSWEBEMGMT, MessageInfo.DEL, servicename);
			mav.addObject("success", true);
			mav.addObject("status", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (Exception e) {
			mav.addObject("success", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}
	
	@RequestMapping("/syswebservicecfg/update.do")
	public ModelAndView updateSysWsCfg(
			User loginUser,HttpServletRequest request,
			@RequestParam("id") String id, 
			@RequestParam("sysid") String sysid, 
			@RequestParam("typeIds") String typeid, 
			@RequestParam("port") String port, 
			@RequestParam("servicename") String servicename,
			@RequestParam("serviceurn") String serviceUrn,
			@RequestParam("namespace") String namespace) {
		ModelAndView mav = new ModelAndView("jsonView");
		try {
			
			sysWebserviceCfgService.update(id,sysid, typeid, port,servicename,serviceUrn,namespace);
			sysLogService.insertLog(loginUser, request, MessageInfo.SYSWEBEMGMT, MessageInfo.DEL, servicename);
			mav.addObject("success", true);
			mav.addObject("status", true);
			mav.addObject("msg", MessageInfo.SAVE_SUCCESS);
			return mav;
		} catch (Exception e) {
			mav.addObject("success", true);
			mav.addObject("status", false);
			mav.addObject("msg", MessageInfo.SAVE_FAILURE);
			return mav;
		}
	}
}
