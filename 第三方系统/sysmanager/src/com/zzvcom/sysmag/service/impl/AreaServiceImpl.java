package com.zzvcom.sysmag.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.persistence.dao.AreaDao;
import com.zzvcom.sysmag.persistence.dao.TaskRecorder;
import com.zzvcom.sysmag.pojo.Area;
import com.zzvcom.sysmag.pojo.PagingResultDTO;
import com.zzvcom.sysmag.pojo.Task;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.AreaService;
import com.zzvcom.sysmag.util.PublicTools;
import com.zzvcom.sysmag.validation.MessageInfo;

/**
 * 区域Service实现类
 * 
 * @author Wang Xiaoming
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;
	@Autowired
	private TaskRecorder recorder;

	public Area loadArea(String areaId) {
		return areaDao.getAreaById(areaId);
	}

	@Transactional(readOnly = true)
	public List<Area> loadAreaList(String areaId, User user) {
		if (areaId.equals("root")) {
			if (user.isAdmin()) {
				return areaDao.getUserDirectAreaList();
			}
			return areaDao.getUserDirectAreaList(user.getUserName());
		} else {
			return areaDao.getLowerAreaList(areaId);
		}

	}

	@Transactional(readOnly = true)
	public PagingResultDTO loadAreaList(int start, int limit, String areaId,
			User user) {
		if (user.isAdmin()) {
			return areaDao.getAreaList(start, limit, areaId);
		}
		return areaDao.getAreaList(start, limit, areaId, user.getUserName());
	}

	@Transactional
	public void removeArea(User loginUser, String areaId) {
		/*
		 * if(isAreaUsed(areaId)) { throw new IllegalDeleteException(new
		 * Area(areaId)); } List<Area> lowerAreaList =
		 * areaDao.getAllLowerAreaList(areaId);
		 */
		/*
		 * 屏蔽原方法，改为下边的分别判断
		if (hasLowerArea(areaId) || isAreaUsed(areaId)) {
			throw new IllegalDeleteException(new Area(areaId));
		}
		*/
		/*if (isAreaUsed(areaId)) {
			throw new IllegalDeleteException(MessageInfo.USING1);
		}*/
		if (hasLowerArea(areaId)) {
			throw new IllegalDeleteException(MessageInfo.USING2);
		}
		
		/*
		 * //删除所有下级区域 for (Area area : lowerAreaList) {
		 * areaDao.deleteArea(area.getAreaId()); }
		 */
		// 删除区域
		areaDao.deleteArea(areaId);

		recorder.record(loginUser, new Area(areaId), Task.OPT_TYPE_DEL);
	}

	@Transactional
	public void saveArea(User loginUser, Area area) {

		area
				.setLastUpdateTime(PublicTools
						.getCurtimeOfString("yyyyMMddHHmmss"));

		if (area.getAreaId().equals("") || null == area.getAreaId()) {
			area.setAreaId(this.newAreaId(area.getUpperAreaId()));
			Area upperArea = areaDao.getAreaById(area.getUpperAreaId());
			area.setAreaLevel(upperArea.getAreaLevel() + 1);
			areaDao.insertArea(area);

			recorder.record(loginUser, area, Task.OPT_TYPE_ADD);
		} else {
			areaDao.updateArea(area);

			recorder.record(loginUser, area, Task.OPT_TYPE_EDIT);
		}

	}

	/**
	 * 按照编号规则生成新的区域Id
	 * 
	 * @param parentId
	 *            上级区域Id
	 * @return 新区域Id
	 */
	private String newAreaId(String parentId) {
		List<Area> areaList = areaDao.getLowerAreaList(parentId);
		int maxCode = 0;
		if (!areaList.isEmpty()) {
			for (Area area : areaList) {
				String[] code = area.getAreaId().split("[.]");
				int currCode = Integer.parseInt(code[code.length - 1]);
				if (currCode > maxCode) {
					maxCode = currCode;
				}
			}
		}

		maxCode++;
		if (parentId.equals("0")) {
			return maxCode + ".";
		}
		return parentId + maxCode + ".";
	}

	/**
	 * 判断区域是否正在使用
	 * 
	 * @param areaId
	 *            区域Id
	 * @return true 使用
	 */
	/*
	private boolean isAreaUsed(String areaId) {
		return areaDao.getUserUsedAreaList(areaId).size() > 0
				|| areaDao.getRoleUsedAreaList(areaId).size() > 0
				|| areaDao.getNeInfoAreaList(areaId).size() > 0;
	}
	*/
	private boolean isAreaUsed(String areaId) {
		return areaDao.getLearningcentersByareaid(areaId).size() > 0;
	}

	private boolean hasLowerArea(String areaId) {
		return areaDao.getLowerAreaList(areaId).size() > 0;
	}

	public boolean inSameUpperArea(List areaList) {
		List<String> upperAreaIds = areaDao
				.getUpperAreaIdListByAreaIds(areaList);
		if (upperAreaIds.isEmpty() || upperAreaIds.size() != 1) {
			return false;
		} else {
			return true;
		}
	}

	public Area getAreaById(String areaId) {
		// TODO Auto-generated method stub
		return areaDao.getAreaById(areaId);
	}

}
