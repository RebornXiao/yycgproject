package com.zzvcom.sysmag.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zzvcom.sysmag.web.CASUrlArgument;

@Controller
public class LogoutController {
	@Resource(name="casUrlArgument") 
	private CASUrlArgument casUrlArgument;
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:"+casUrlArgument.getLogOutUrl();
	}
	
}
