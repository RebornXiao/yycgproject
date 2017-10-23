package com.zzvcom.sysmag.pojo;

public class Module {
	public static final String ROOT_MODULE_ID = "0";
	
	private String moduleId;
	private String moduleName;
	private String systemId;
	private String systemName;
	private String upperModuleId;
	private String upperModuleName;
	private String url;
	private String icon;
	private int showOrder;
	private String isUsed;
	
	public Module() {
		
	}
	
	public Module(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public Module(String moduleId, String moduleName) {
		this.moduleId = moduleId;
		this.moduleName = moduleName;
	}
	
	public boolean isTopModule() {
		return this.upperModuleId.equals(ROOT_MODULE_ID);
	}
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getUpperModuleId() {
		return upperModuleId;
	}
	public void setUpperModuleId(String upperModuleId) {
		this.upperModuleId = upperModuleId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		/*String _icon = icon;
		if (icon == null || icon.equals("")) {
			_icon = "image/icons/package_green.png";
		}
		this.icon = _icon;*/
		this.icon = icon;
	}
	
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	
	/**
	 * 判断与目标模块是否属于同一子系统
	 * @param module 目标模块
	 * @return true 属于同一子系统
	 */
	public boolean inSameSystemWith(Module module) {
		return systemId.equals(module.getSystemId());
	}
	
	/**
	 * 判断与目标模块是否属于同一上级模块
	 * @param module 目标模块
	 * @return true 属于同一模块
	 */
	public boolean inSameTopModuleWith(Module module) {
		return upperModuleId.equals(module.getUpperModuleId());
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Module) {
			Module module = (Module)obj;
			if (this.moduleId.equals(module.getModuleId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getUpperModuleName() {
		return upperModuleName;
	}

	public void setUpperModuleName(String upperModuleName) {
		this.upperModuleName = upperModuleName == null ? "无":upperModuleName;
	}
	
	
	
}
