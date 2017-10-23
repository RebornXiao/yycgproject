package com.zzvcom.sysmag.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzvcom.sysmag.persistence.dao.AreaDao;
import com.zzvcom.sysmag.pojo.Area;

@Component
public class AreaValidator implements Validator {
	private Area vldtArea;
	private Area rsltArea;
	private Errors errors;
	@Autowired private AreaDao areaDao;
	
	public Errors validate(Object validateObj) {
		errors = new Errors();
		vldtArea = (Area)validateObj;
		//rsltArea  = areaDao.getAreaByName(vldtArea.getAreaName());
		/*在同一父级区域下查找是否存在相同区域名称*/
		List<Area> rsltAreaList = areaDao.getLowerAreaList(vldtArea.getUpperAreaId());
		rsltAreaList.add(areaDao.getAreaById(vldtArea.getUpperAreaId()));
		if (!rsltAreaList.isEmpty()) {
			for (Area area : rsltAreaList) {
				rsltArea = area;
				if(rsltArea != null){
					if (rsltArea.getAreaName().equals(vldtArea.getAreaName()) && !rsltArea.equals(vldtArea)) {
						errors.reject("areaName", MessageInfo.EXSITS_AREANAME);
					}	
				}
				
			}
		}
		
		/**
		 * 检查运营商编号是否已经存在
		 */
/*		rsltArea = areaDao.getAreaByTeleComCode(vldtArea.getTelecomCode());
		if (rsltArea != null) {
			if (!rsltArea.equals(vldtArea)) {
				errors.reject("telecomCode", MessageInfo.EXSITS_TELECOMCODE);
			}
		}*/
		
		return errors;
	}

}
