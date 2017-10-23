package com.zzvcom.sysmag.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.pojo.TreeNode;

/**
 * 树节点列表查找器
 * @author Wang Xiaoming
 */
@Component
public class TreeListSeeker {
	private int checkedNodeCount = 0;
	private int allNodeCount = 0;
	
	/**
	 * 计算树节点数目
	 * @param treeList 树节点列表
	 * @return 数目
	 */
	public Map<String,Integer> countCheckedNode(List<TreeNode> treeList) {
		this.checkedNodeCount = 0;
		this.allNodeCount = 0;
		
		count(treeList);
		
		Map<String, Integer> count = new HashMap<String, Integer>();
		count.put("checkedCount", checkedNodeCount);
		count.put("allCount", allNodeCount);
		return count;
	}
	
	private void count(List<TreeNode> treeList) {
		for (TreeNode treeNode : treeList) {
			if (treeNode.isChecked()) {
				checkedNodeCount ++;
			}
			allNodeCount ++;
			
			if (treeNode.hasChild()) {
				count(treeNode.getChildren());
			}
		}
	}
}
