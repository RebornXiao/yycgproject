package zzvcom.sys.service;

import java.util.Date;
import java.util.List;

import zzvcom.sys.pojo.Dictinfo;
import zzvcom.sys.pojo.DictinfoExample;
import zzvcom.sys.pojo.Dicttype;

public interface ManagerSystemConfig {

	
	
	/**
	 * 查询字典类别列表
	 * 
	 */
	public List<Dicttype> findDicttypelist() throws Exception;
	
	/**
	 * 根据编码查询字典类别
	 * 
	 */
	public Dicttype getDicttypeByCode(String typecode) throws Exception;
	
	/**
	 * 根据ID查询字典类别信息
	 * 
	 */
	public Dictinfo getDictinfoById(String dictid) throws Exception;
	
	/**
	 * 根据编码查询字典类别信息
	 * 
	 */
	public List<Dictinfo> getDictinfoByCode(String dictcode) throws Exception;
	
	
	/**
	 * 根据典类别参数查询字典类别信息
	 * 
	 */
	public List<Dictinfo> getDictinfoByDictinfopars(Dictinfo dictinfo) throws Exception;
	
	/**
	 * 添加数据字典类别
	 * 
	 */
	public void addDicttype(Dicttype dicttype) throws Exception;
	
	
	/**
	 * 添加数据字典类别信息
	 * 
	 */
	public void addDictinfo(Dictinfo dictinfo) throws Exception;
	
	/**
	 * 更新数据字典类别
	 * 
	 */
	public void updateDicttype(Dicttype dicttype) throws Exception;
	
	/**
	 * 更新数据字典类别信息
	 * 
	 */
	public void updateDictinfo(Dictinfo dictinfo) throws Exception;
	/**
	 * 删除数据字典类别
	 * 
	 */
	public void delDicttype(List typecodelist) throws Exception;
	
	/**
	 * 删除数据字典类别信息
	 * 
	 */
	public void delDictinfo(List idlist) throws Exception;
	
	
	
	/**
	 * 根据类型编码、学校编码id查询字典类别详细详细列表
	 * 
	 */
	public List<Dictinfo> findDicttypeinfolist(String typecode) throws Exception;
	
    /**
     * 设置可用或者不可用状态
     * 
     */
    public void updateSetDictinfoState(List idslist,String state) throws Exception;
    
    List<Dictinfo> getDictinfoByDictinfopars(DictinfoExample dictinfoexample)throws Exception;
	
}
