package com.zzvcom.sysmag.packer;

import java.util.ArrayList;
import java.util.List;

import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.TreeNode;

public class DeployNodeTree {
	public static List<TreeNode> pack(List<SubSystem> systemList) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (SubSystem system : systemList) {
			TreeNode node = new TreeNode();
			node.setId(system.getSystemId());
			node.setText(system.getSystemName());
			node.setIcon("image/icons/monitor.png");
			node.setLeaf(true);
			treeList.add(node);
		}
		return treeList;
	}
}
