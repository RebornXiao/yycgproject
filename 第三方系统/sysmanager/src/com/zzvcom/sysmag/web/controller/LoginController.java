package com.zzvcom.sysmag.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zzvcom.sysmag.packer.ModuleMenuTreePacker;
import com.zzvcom.sysmag.pojo.TreeNode;

@Controller
public class LoginController {

	@RequestMapping("/enter.do")
	public ModelAndView login(HttpServletRequest request) {
//		setMockSession(request);
		return new ModelAndView("index");
	}

	@RequestMapping("/login/loadMenu.do")
	public ModelAndView loadMenu(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("jsonTreeView");
		
		List moduleAuthList = (List) request.getSession().getAttribute("modulelist");

		ModuleMenuTreePacker packer = new ModuleMenuTreePacker();
		List<TreeNode> menuTree = packer.packMenuTree(moduleAuthList);

		mav.addObject("tree", menuTree);
		mav.addObject("excluded", "children,checked");
		return mav;
	}

	/*
	 * @RequestMapping("/login/loadMenu.do") public ModelAndView
	 * loadMenuTree(HttpSession session) { List<Map> moduleList = (List)
	 * session.getAttribute("modulelist"); ModelAndView mav = new
	 * ModelAndView("jsonTreeView");
	 * mav.addObject("tree",loginBean.loadMenu(moduleList));
	 * mav.addObject("excluded", "checked"); return mav; }
	 */

	private void setMockSession(HttpServletRequest req) {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("moduleid", "1");
		map1.put("modulename", "用户管理");
		map1.put("moduleurl", "user/show.do");
		map1.put("morderid", "1");
		map1.put("operateid", "add_user,edit_user,delete_user");

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("moduleid", "2");
		map2.put("modulename", "区域管理");
		map2.put("moduleurl", "area/show.do");
		map2.put("morderid", "2");
		map2.put("operateid", "add_area,edit_area,delete_area,");

		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("moduleid", "3");
		map3.put("modulename", "模块管理");
		map3.put("moduleurl", "module/show.do");
		map3.put("morderid", "3");
		map3.put("operateid", "add_module,edit_module,del_module,");

		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("moduleid", "4");
		map4.put("modulename", "角色管理");
		map4.put("moduleurl", "role/show.do");
		map4.put("morderid", "4");
		map4.put("operateid", "add_role,edit_role,delete_role,assign_role,");

		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("moduleid", "5");
		map5.put("modulename", "节点管理");
		map5.put("moduleurl", "deployNode/show.do");
		map5.put("morderid", "5");
		map5.put("operateid", "add_node,edit_node,del_note,");

		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("moduleid", "6");
		map6.put("modulename", "业务管理");
		map6.put("moduleurl", "business/show.do");
		map6.put("morderid", "6");
		map6.put("operateid", "add_buss,edit_buss,del_buss,");

		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("moduleid", "7");
		map7.put("modulename", "子系统Web Servies配置");
		map7.put("moduleurl", "syswebservicecfg/show.do");
		map7.put("morderid", "7");
		map7.put("operateid", "add_cfg,edit_cfg,del_cfg,");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);

		HttpSession session = req.getSession();
		session.setAttribute("modulelist", list);

		Map userMap = new HashMap();
		userMap.put("username", "测试用户1");
		Map roleMap = new HashMap();
		roleMap.put("roleid", "125704");
		roleMap.put("rolename", "测试1");

		session.setAttribute("user", userMap);
		session.setAttribute("role", roleMap);
	}

}
