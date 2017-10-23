package com.zzvcom.sysmag.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 进入其他子系统导航控制器
 * @author Wang Xiaoming
 */
@Controller
public class NavigationController {
	@RequestMapping("/navigation/loadDeployNode.do")
	public ModelAndView loadDnNavigationList(HttpSession session) {
		ModelAndView mav = new ModelAndView("jsonView");
		List<Map> nodeInfoList = setNodeInfoList(session);
		mav.addObject("rows", nodeInfoList);
		return mav;
	}
	
	private List<Map> setNodeInfoList(HttpSession session) {
		List<Map> mockList = new ArrayList<Map>();
		mockList = (List<Map>)session.getAttribute("sysNodeByUserList");
		return mockList;
	}
}
