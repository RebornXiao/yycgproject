package com.zzvcom.sysmag.pojo;

import java.util.Comparator;
import java.util.Map;

public class ModuleSorter implements Comparator<Map> {
	public int compare(Map moduleMap1, Map moduleMap2) {
		int order1 = Integer.parseInt((String)moduleMap1.get("morderid"));
		int order2 = Integer.parseInt((String)moduleMap2.get("morderid"));
		if (order1 > order2) {
			return 1;
		} else if (order1 < order2) {
			return -1;
		} else {
			return 0;
		}
	}

}
