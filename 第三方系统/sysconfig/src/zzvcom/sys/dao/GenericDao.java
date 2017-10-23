package zzvcom.sys.dao;

import zzvcom.sys.dao.exception.DeleteException;
import zzvcom.sys.dao.exception.ObjectNotFindException;
import zzvcom.sys.dao.exception.SaveOrUpdateException;
/**
 * 数据访问通用接口
 * 
 * @author LHQ.
 *
 * @param <T>
 */
public interface GenericDao<T>
{
	/**
	 * 通过根据id获取对象
	 * @param id
	 * @return
	 */
	T query(String id) throws ObjectNotFindException;

	/**
	 * 通用保持实体对象
	 * @param entity
	 * @throws SaveOrUpdateException
	 */
	String create(T entity)throws SaveOrUpdateException;

	/**
	 * 通用删除对象
	 * @param entity
	 * @throws DeleteException
	 */
	void delete(T entity)throws DeleteException;

	/**
	 * 通用修改或者保存对象
	 * @param entity
	 * @throws SaveOrUpdateException
	 */
	void saveOrupdate(T entity)throws SaveOrUpdateException;
	
}
