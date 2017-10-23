package com.zzvcom.sysmag.packer;

import java.util.ArrayList;
import java.util.List;

import com.zzvcom.sysmag.pojo.Business;
import com.zzvcom.sysmag.pojo.TreeNode;

public class BusinessTreePacker {
	public static List<TreeNode> pack(List<Business> controllBussList, List<Business> assignedBussList ) {
		List<TreeNode> bussTreeList = new ArrayList<TreeNode>();
		for (Business business : controllBussList) {
			TreeNode node = new TreeNode();
			node.setId(business.getBusinessCode());
			node.setText(business.getBusinessName());
			node.setLeaf(true);
			if (assignedBussList.contains(business)) {
				node.setChecked(true);
			}
			bussTreeList.add(node);
		}
		return bussTreeList;
	}
}
