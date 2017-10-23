package com.zzvcom.sysmag.packer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.ModuleSorter;
import com.zzvcom.sysmag.pojo.TreeNode;

public class ModuleMenuTreePacker {
	private List<Map> menuList = new ArrayList<Map>();
	
	public List<TreeNode> packMenuTree(List<Map> _menuList) {
		return packModuleTree(sortModuleListByOrder(filterUpperModule(_menuList)));
	}
	
	/**
	 * 过滤掉一级模块
	 * @param moduleAuthList
	 */
	private List<Map> filterUpperModule(List<Map> moduleAuthList) {
		List<Map> afterFilteredmoduleList = new ArrayList<Map>();
		
		for (Object object : moduleAuthList) {
			Map moduleMap = (Map)object;
			String upperModuleId = (String)moduleMap.get("parentid");
			if (!upperModuleId.equals(Module.ROOT_MODULE_ID)) {
				afterFilteredmoduleList.add(moduleMap);
			}
			
		}
		return afterFilteredmoduleList;
	}
	
	/**
	 * 包装模块树数据
	 * @param moduleList
	 * @return
	 */
	private List<TreeNode> packModuleTree(List<Map> moduleList) {
		List<TreeNode> menu = new ArrayList<TreeNode>();
		for (Map moduleMap : moduleList) {
			TreeNode node = new TreeNode();
			node.setId(moduleMap.get("moduleid").toString());
			node.setText(moduleMap.get("modulename").toString());
			node.setHref(moduleMap.get("moduleurl") + "?oid=" + moduleMap.get("operateid"));
			node.setLeaf(true);
			node.setIconCls("node-module");
			menu.add(node);
		}
		return menu;
	}
	
	/**
	 * 按照模块的showOrder属性对模块列表进行排序
	 * @param moduleList
	 */
	private List<Map> sortModuleListByOrder(List<Map> moduleList) {
		List<Map> afterSortModuleList = moduleList;
		ModuleSorter moduleSorter = new ModuleSorter();
		Collections.sort(afterSortModuleList, moduleSorter);
		return afterSortModuleList;
	}
}
