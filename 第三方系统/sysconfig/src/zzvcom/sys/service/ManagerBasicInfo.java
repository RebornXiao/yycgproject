package zzvcom.sys.service;

import java.util.HashMap;
import java.util.List;

import zzvcom.sys.pojo.Basicinfo;



public interface ManagerBasicInfo {

//	// 根据id删除信息
//	public void delBasicInfoById(String id) throws Exception;
//
//	// 根据id得到对象信息
//	public Basicinfo getBasicInfoByID(String id) throws Exception;
//
//	// 根据属性类型和分组标识查询基础配置信息
//	@SuppressWarnings("unchecked")
//	public HashMap findBasicInfoByTagList(String type, String tag)
//			throws Exception;
//
//	public List findBasicInfoByType(String type, String tag) throws Exception;

	/**
	 * 更新基础配置信息
	 * 
	 * @return
	 * @throws Exception
	 */

	public int updateBasicinfo(Basicinfo basicinfo) throws Exception;

//	// 根据项目名称得到对象信息
//	public Basicinfo getBasicInfoByProjectname(String projectname)
//			throws Exception;
	
	/**
	 * 查询可修改基础配置项目
	 * @return 列表
	 * @throws Exception
	 */
	public List<Basicinfo> findeditprojectList() throws Exception;
	
	Basicinfo findBasicinfoById(String id);

}
