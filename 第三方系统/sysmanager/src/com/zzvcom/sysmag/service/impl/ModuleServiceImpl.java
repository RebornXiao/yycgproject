package com.zzvcom.sysmag.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzvcom.sysmag.exception.IllegalDeleteException;
import com.zzvcom.sysmag.persistence.dao.ModuleDao;
import com.zzvcom.sysmag.persistence.dao.OperationDao;
import com.zzvcom.sysmag.persistence.dao.SubSystemDao;
import com.zzvcom.sysmag.persistence.dao.TaskRecorder;
import com.zzvcom.sysmag.pojo.MenuElement;
import com.zzvcom.sysmag.pojo.Module;
import com.zzvcom.sysmag.pojo.Operation;
import com.zzvcom.sysmag.pojo.Role;
import com.zzvcom.sysmag.pojo.SubSystem;
import com.zzvcom.sysmag.pojo.Task;
import com.zzvcom.sysmag.pojo.User;
import com.zzvcom.sysmag.service.ModuleService;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	private SubSystemDao systemDao;
	@Autowired
	private ModuleDao moduleDao;
	@Autowired
	private OperationDao operationDao;
	
	@Autowired private TaskRecorder recorder;
	
	public List<Module> loadModuleList(Role role, String systemId,
			String upperModuleId) {
		return moduleDao.getModuleList(systemId, upperModuleId);
	}
	
	public List<Operation> loadOperationList(Role role, String systemId,
			String bottomModuleId) {
		return operationDao.getOperationList(bottomModuleId);
	}
	
	@Transactional(readOnly=true)
	public List<SubSystem> loadSystemList(Role role) {
		return systemDao.getSystemList();
	}

	@Transactional
	public void saveModule(Module module, String cmd) {
		if (cmd.equals("add")) {
			moduleDao.insertModule(module);
		} else {
			moduleDao.updateModule(module);
		}

	}

	@Transactional
	public void saveOperation(User loginUser, Operation operation, String cmd) {
		if (cmd.equals("add")) {
			operationDao.insertOperation(operation);
			
			//记录数据同步任务
			//recorder.record(loginUser, operation, Task.OPT_TYPE_ADD);
		} else {
			operationDao.updateOperation(operation);
			
			//记录数据同步任务
			//recorder.record(loginUser, operation, Task.OPT_TYPE_EDIT);
		}

	}

	@Transactional(readOnly=true)
	public Map load(Role role, String eleStr) {
		Map result = new HashMap();
		MenuElement element = new MenuElement(eleStr);
		// 如果传入的类型为根, 用子系统列表填充菜单列表
		if (element.isRoot()) {
			result.put("type", MenuElement.SYSTEM_TYPE);
			result.put("data", loadSystemList(role));
			return result;
		}

		// 如果传入的类型为子系统, 用一级模块列表填充菜单列表
		if (element.isSystem()) {
			result.put("type", MenuElement.TOP_MODULE_TYPE);
			result.put("data", loadModuleList(role, element.getId(),
					Module.ROOT_MODULE_ID));
			return result;
		}

		// 如果传入的类型为一级模块
		if (element.isTopModule()) {
			Module comeModule = moduleDao.getModuleById(element.getId()); // 加载该模块信息
			result.put("type", MenuElement.BOTTOM_MODULE_TYPE);
			result.put("data", loadModuleList(role, comeModule.getSystemId(),
					comeModule.getModuleId()));
			return result;
		}
		
		// 如果传入的类型为二级模块
//		if (element.isMiddleModule()) {
//			Module comeModule = moduleDao.getModuleById(element.getId()); // 加载该模块信息
//			result.put("type", MenuElement.BOTTOM_MODULE_TYPE);
//			result.put("data", loadModuleList(role, comeModule.getSystemId(),
//					comeModule.getModuleId()));
//			return result;
//		}

		// 如果模块为三级模块
		if (element.isBottomModule()) {
			Module comeModule = moduleDao.getModuleById(element.getId()); // 加载该模块信息
			result.put("type", MenuElement.OPERATION_TYPE);
			result.put("data", loadOperationList(role,comeModule.getSystemId(), comeModule.getModuleId()));
			return result;
		}
		return Collections.EMPTY_MAP;
	}
	
	
	@Transactional
	public void removeModule(String moduleId) {
		Module module = moduleDao.getModuleById(moduleId);
		if (/*isUsedModule(module) || */hasLowerModule(module) /*|| hasOperation(module)*/) {
			throw new IllegalDeleteException(module);
		}
		//moduleDao.deleteModule(moduleId);
		
		operationDao.deleteOperationByModuleid(moduleId);
		moduleDao.deleteModule(moduleId);
	}

	@Transactional
	public void removeOperation(User loginUser, String operationId) {
		/*if (isUsedOperation(operationId)) {
			throw new IllegalDeleteException(operationId);
		}*/
		operationDao.deleteOperation(operationId);
		
		//recorder.record(loginUser, new Operation(operationId), Task.OPT_TYPE_DEL);

	}

	@Transactional(readOnly = true)
	private boolean isUsedModule(Module module) {
		List<Module> moduleList = moduleDao.getUsedModuleList(module
				.getModuleId());
		if (moduleList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Transactional(readOnly = true)
	private boolean hasLowerModule(Module module) {
		List<Module> lowerModuleList = moduleDao.getModuleList(module
				.getSystemId(), module.getModuleId());
		return !lowerModuleList.isEmpty();
	}

	@Transactional(readOnly = true)
	private boolean hasOperation(Module module) {
		List<Operation> optList = operationDao.getOperationList(module.getModuleId());
		return !optList.isEmpty();
	}

	@Transactional(readOnly = true)
	private boolean isUsedOperation(String operationId) {
		List<Operation> optList = operationDao
				.getUsedOperationList(operationId);
		return !optList.isEmpty();
	}

	@Transactional
	public void saveOrder(String itemIds, String type) {
		String[] items = itemIds.split(",");
		if (type.equals("m")) {
			for (int i = 0; i < items.length; i++) {
				Module module = new Module(items[i]);
				module.setShowOrder(i+1);
				moduleDao.saveModuleOrder(module);
			}
		} else {
			for (int i = 0; i < items.length; i++) {
				Operation operation = new Operation(items[i]);
				operation.setShowOrder(i+1);
				operationDao.saveOperationOrder(operation);
			}
			
		}
		
	}

	public Module getModuleById(String moduleId) {
		// TODO Auto-generated method stub
		return moduleDao.getModuleById(moduleId);
	}

	public Operation getOperationById(String operationId) {
		// TODO Auto-generated method stub
		return operationDao.getOperationById(operationId);
	}

}
