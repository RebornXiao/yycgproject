package com.zzvcom.sysmag.packer;

import java.util.ArrayList;
import java.util.List;

import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.TreeNode;

public class AreaTreePacker {
	public static List<TreeNode> packArea(List<Area> areaList) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Area area : areaList) {
			TreeNode node = new TreeNode();
			node.setId(area.getAreaId());
			node.setText(area.getAreaName());
			treeList.add(node);
		}
		return treeList;
	}
}
