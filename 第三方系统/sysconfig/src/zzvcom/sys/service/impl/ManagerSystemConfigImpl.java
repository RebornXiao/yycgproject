package zzvcom.sys.service.impl;

import java.util.List;

import zzvcom.log.util.UUIDBuild;
import zzvcom.sys.dao.DictinfoDAO;
import zzvcom.sys.dao.DicttypeDAO;
import zzvcom.sys.pojo.Dictinfo;
import zzvcom.sys.pojo.DictinfoExample;
import zzvcom.sys.pojo.Dicttype;
import zzvcom.sys.pojo.DicttypeExample;
import zzvcom.sys.service.ManagerSystemConfig;

public class ManagerSystemConfigImpl implements
		ManagerSystemConfig {

	
	private DicttypeDAO dicttypedao;

	private DictinfoDAO dictinfodao;
	

	public DicttypeDAO getDicttypedao() {
		return dicttypedao;
	}

	public void setDicttypedao(DicttypeDAO dicttypedao) {
		this.dicttypedao = dicttypedao;
	}

	public DictinfoDAO getDictinfodao() {
		return dictinfodao;
	}

	public void setDictinfodao(DictinfoDAO dictinfodao) {
		this.dictinfodao = dictinfodao;
	}

	/**
	 * 添加数据字典类别
	 * 
	 * @throws Exception
	 */
	public void addDicttype(Dicttype dicttype) throws Exception {
		dicttypedao.insert(dicttype);
	}

	/**
	 * 根据编码查询字典类别
	 * 
	 */
	public Dicttype getDicttypeByCode(String typecode) throws Exception {
		// TODO Auto-generated method stub
		return dicttypedao.selectByPrimaryKey(typecode);

	}

	public List<Dicttype> findDicttypelist() throws Exception {
		DicttypeExample dicttypeexample = new DicttypeExample();
		dicttypeexample.setOrderByClause("typesort");
		return dicttypedao.selectByExample(dicttypeexample);
	}

	/**
	 * 更新数据字典类别
	 * 
	 */
	public void updateDicttype(Dicttype dicttype) throws Exception {
		dicttypedao.updateByPrimaryKey(dicttype);

	}

	/**
	 * 删除数据字典类别
	 * 
	 */
	public void delDicttype(List typecodelist) throws Exception {
		DicttypeExample dicttypeexample = new DicttypeExample();
		zzvcom.sys.pojo.DicttypeExample.Criteria dicttypeCriteria = dicttypeexample.createCriteria();
		dicttypeCriteria.andTypecodeIn(typecodelist);
		dicttypedao.deleteByExample(dicttypeexample);
	}

	/**
	 * 查询字典类别详细详细列表
	 * 
	 */
	public List<Dictinfo> findDicttypeinfolist(String typecode)
			throws Exception {

		DictinfoExample dictinfoexample = new DictinfoExample();
		zzvcom.sys.pojo.DictinfoExample.Criteria dictinfoCriteria = dictinfoexample
				.createCriteria();
		dictinfoCriteria.andTypecodeEqualTo(typecode);
        dictinfoexample.setOrderByClause("dictsort");

		return dictinfodao.selectByExample(dictinfoexample);
	}

	/**
	 * 根据id查询字典类别信息
	 * 
	 */
	public Dictinfo getDictinfoById(String dictid) throws Exception {
		// TODO Auto-generated method stub
		return dictinfodao.selectByPrimaryKey(dictid);
	}

	/**
	 * 根据编码查询字典类别信息
	 * 
	 */
	public List<Dictinfo> getDictinfoByCode(String dictcode) throws Exception {
		DictinfoExample dictinfoexample = new DictinfoExample();
		zzvcom.sys.pojo.DictinfoExample.Criteria dictinfoCriteria = dictinfoexample
				.createCriteria();
		dictinfoCriteria.andDictcodeEqualTo(dictcode);
		return dictinfodao.selectByExample(dictinfoexample);

	}

	public void addDictinfo(Dictinfo dictinfo) throws Exception {
		// 插入对象的主键值
		dictinfo.setId(UUIDBuild.getUUID());
		dictinfodao.insert(dictinfo);

	}

	/**
	 * 更新数据字典类别信息
	 * 
	 */
	public void updateDictinfo(Dictinfo dictinfo) throws Exception {
		// TODO Auto-generated method stub
		dictinfodao.updateByPrimaryKey(dictinfo);
	}

	/**
	 * 删除数据字典类别信息
	 * 
	 */
	public void delDictinfo(List idlist) throws Exception {
		DictinfoExample dictinfoexample = new DictinfoExample();
		zzvcom.sys.pojo.DictinfoExample.Criteria dictinfoCriteria = dictinfoexample
				.createCriteria();
		dictinfoCriteria.andIdIn(idlist);
		dictinfodao.deleteByExample(dictinfoexample);

	}

	public List<Dictinfo> getDictinfoByDictinfopars(DictinfoExample dictinfoexample)throws Exception{
		return dictinfodao.selectByExample(dictinfoexample);
	}

	/**
	 * 根据典类别参数查询字典类别信息
	 * 
	 */
	public List<Dictinfo> getDictinfoByDictinfopars(Dictinfo dictinfo)
			throws Exception {
      
		DictinfoExample dictinfoexample = new DictinfoExample();
		zzvcom.sys.pojo.DictinfoExample.Criteria dictinfoCriteria = dictinfoexample
				.createCriteria();
        
		  if(dictinfo!=null){
			  String dictcode = dictinfo.getDictcode();
			  String info = dictinfo.getInfo();
			  String typecode = dictinfo.getTypecode();
			  String id = dictinfo.getId();
              if(info!=null&&!info.equals("")){
                  dictinfoCriteria.andInfoEqualTo(info);
                  dictinfoCriteria.andTypecodeEqualTo(typecode);
              }
			  if(dictcode!=null&&!dictcode.equals("")){
                  zzvcom.sys.pojo.DictinfoExample.Criteria dictinfoCriteria1 = dictinfoexample
                    .createCriteria();
                  dictinfoCriteria1.andDictcodeEqualTo(dictcode);
                  dictinfoCriteria1.andTypecodeEqualTo(typecode);
                  if(id!=null&&!id.equals("")){
                      dictinfoCriteria1.andIdNotEqualTo(id);
                  }
                  dictinfoexample.or(dictinfoCriteria1);
			  }
			  if(id!=null&&!id.equals("")){
				  dictinfoCriteria.andIdNotEqualTo(id);
			  }
			  
	        }
		
		return dictinfodao.selectByExample(dictinfoexample);

	
	}

    public void updateSetDictinfoState(List idslist, String state) throws Exception
    {
        for(int m=0;m<idslist.size();m++){
            String id = (String)idslist.get(m);
            Dictinfo newdict = new Dictinfo();
            newdict.setId(id);
            newdict.setIsenable(state);
            dictinfodao.updateByPrimaryKeySelective(newdict);
        }
    }

}