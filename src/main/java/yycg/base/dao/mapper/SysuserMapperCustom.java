package yycg.base.dao.mapper;

import java.util.List;

import yycg.base.po.Sysuser;
import yycg.base.vo.Menu;
import yycg.base.vo.Operation;
import yycg.base.vo.SysuserCustom;
import yycg.base.vo.SysuserQueryVo;

public interface SysuserMapperCustom {
	/**
	 * findSysuserList名称和mapper.xml的id保持一致
	 * @param sysuser
	 * @return
	 * @throws Exception
	 */
	public List<SysuserCustom>  findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception;
	public int findSysuserCount(SysuserQueryVo sysuserQueryVo)throws Exception;
	/**
	 * 根据用户角色获取菜单
	 */
	public List<Menu> findMenuByroleid(String roleid) throws Exception;
	
	/**
	 * 根据用户角色获取操作权限
	 */
	public List<Operation> findOperatByRoleid(String roleid) throws Exception;
}
