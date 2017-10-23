package zzvcom.sys.service.impl;

import java.util.HashMap;
import java.util.List;

import zzvcom.sys.dao.BasicinfoDAO;
import zzvcom.sys.pojo.Basicinfo;
import zzvcom.sys.pojo.BasicinfoExample;
import zzvcom.sys.pojo.BasicinfoExample.Criteria;
import zzvcom.sys.service.ManagerBasicInfo;



public class ManagerBasicInfoImpl implements
		ManagerBasicInfo {

	private BasicinfoDAO basicinfoDAO;

	public BasicinfoDAO getBasicinfoDAO() {
		return basicinfoDAO;
	}


	public void setBasicinfoDAO(BasicinfoDAO basicinfoDAO) {
		this.basicinfoDAO = basicinfoDAO;
	}


	/**
	 * 更新基础配置信息
	 * 
	 * @return
	 * @throws Exception
	 */

	public int updateBasicinfo(Basicinfo basicinfo) throws Exception {
		if (null != basicinfo) {
			String id = basicinfo.getId();
			Basicinfo curbasicinfo = findBasicinfoById(id);
			curbasicinfo.setValue(basicinfo.getValue());
			return basicinfoDAO.updateByPrimaryKey(curbasicinfo);
		}
		return 0;
	}

	
	/**
	 * 查询可修改基础配置项目
	 * @return 列表
	 * @throws Exception
	 */
	public List<Basicinfo> findeditprojectList() throws Exception {
		BasicinfoExample basicinfoExample = new BasicinfoExample();
		Criteria criteria = basicinfoExample.createCriteria();
		criteria.andIsaliveEqualTo("1");
		criteria.andVchar2EqualTo("1");
		basicinfoExample.setOrderByClause("type");
		return  basicinfoDAO.selectByExample(basicinfoExample);
		
	}
	
	public Basicinfo findBasicinfoById(String id) {
		return basicinfoDAO.selectByPrimaryKey(id);
	}


}