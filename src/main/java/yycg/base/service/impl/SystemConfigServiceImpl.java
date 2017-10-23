package yycg.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import yycg.base.dao.mapper.DictinfoMapper;
import yycg.base.po.Dictinfo;
import yycg.base.po.DictinfoExample;
import yycg.base.service.SystemConfigService;

public class SystemConfigServiceImpl  implements SystemConfigService{

	@Autowired
	DictinfoMapper dictinfoMapper;
	//根据数据字典typecode获取字典明细信息
	@Override
	public List findDictinfoByType(String typecode) throws Exception {

		DictinfoExample dictinfoExample = new DictinfoExample();
		DictinfoExample.Criteria criteria = dictinfoExample.createCriteria();
		
		criteria.andTypecodeEqualTo(typecode);
		
		return dictinfoMapper.selectByExample(dictinfoExample);
		
	}
	//根据typeocde和dictcode获取单个字典明细
	public Dictinfo  findDictinfoByDictcode(String typecode,String dictcode) throws Exception{
		DictinfoExample dictinfoExample = new DictinfoExample();
		DictinfoExample.Criteria criteria = dictinfoExample.createCriteria();
		criteria.andDictcodeEqualTo(dictcode);
		criteria.andTypecodeEqualTo(typecode);
		List<Dictinfo> list = dictinfoMapper.selectByExample(dictinfoExample);
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
		
	}

}
