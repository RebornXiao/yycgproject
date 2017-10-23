package com.zzvcom.sysmag.packer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.pojo.MenuElement;
import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.TreeNode;

/**
 * 数据包装器 将系统、模块、操作数据包装成树
 * 
 * @author Wang Xiaoming
 */
@Component("moduleTreePacker")
public class ModuleTreePacker {
	private List<TreeNode> treeList = new ArrayList<TreeNode>();
	private List dataList = new ArrayList();

	public List<TreeNode> pack(Map result) {
		String type = (String) result.get("type");
		dataList = (List) result.get("data");
		treeList = packWithCorrectMethod(type);
		return treeList;

	}

	private List<TreeNode> packWithCorrectMethod(String type) {
		if (type.equals(MenuElement.SYSTEM_TYPE)) {
			return packSystem(dataList);
		}
		if (type.equals(MenuElement.TOP_MODULE_TYPE)) {
			return packTopModule(dataList);
		}
		/*if (type.equals(MenuElement.MIDDLE_MODULE_TYPE)) {
			return packMiddleModule(dataList);
		}*/
		if (type.equals(MenuElement.BOTTOM_MODULE_TYPE)) {
			return packBottomModule(dataList);
		}
		if (type.equals(MenuElement.OPERATION_TYPE)) {
			return packOperation(dataList);
		}
		return Collections.EMPTY_LIST;
	}

	private List<TreeNode> packSystem(List list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Object object : list) {
			SubSystem system = (SubSystem) object;
			//过滤掉子系统树中的统一门户
			if (!system.getSystemId().equals("1")) {
				TreeNode node = new TreeNode();
				node.setId(MenuElement.SYSTEM_TYPE + system.getSystemId());
				node.setText(system.getSystemName());
				node.setIconCls("node-system-normal");
				// node.setIcon("image/icons/monitor.png");
				treeList.add(node);
			}
		}
		return treeList;
	}

	private List<TreeNode> packTopModule(List list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Object object : list) {
			Module module = (Module) object;
			TreeNode node = new TreeNode();
			node.setId(MenuElement.TOP_MODULE_TYPE + module.getModuleId());
			node.setText(module.getModuleName());
			node.setIconCls("node-module");
			// node.setIcon(module.getIcon());
			treeList.add(node);
		}
		return treeList;
	}
	
	/*private List<TreeNode> packMiddleModule(List list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Object object : list) {
			Module module = (Module) object;
			TreeNode node = new TreeNode();
			node.setId(MenuElement.MIDDLE_MODULE_TYPE + module.getModuleId());
			node.setText(module.getModuleName());
			node.setIconCls("node-module");
			// node.setIcon(module.getIcon());
			treeList.add(node);
		}
		return treeList;
	}*/

	private List<TreeNode> packBottomModule(List list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Object object : list) {
			Module module = (Module) object;
			TreeNode node = new TreeNode();
			node.setId(MenuElement.BOTTOM_MODULE_TYPE + module.getModuleId());
			node.setText(module.getModuleName());
			node.setIconCls("node-module-bottom");
			// node.setIcon(module.getIcon());
			node.setLeaf(true);
			treeList.add(node);
		}
		return treeList;
	}

	private List<TreeNode> packOperation(List list) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Object object : list) {
			Operation operation = (Operation) object;
			TreeNode node = new TreeNode();
			node.setId(MenuElement.OPERATION_TYPE + operation.getOperationId());
			node.setText(operation.getOperationName());
			node.setLeaf(true);
			treeList.add(node);
		}
		return treeList;
	}

}
