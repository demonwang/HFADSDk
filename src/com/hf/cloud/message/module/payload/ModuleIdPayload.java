package com.hf.cloud.message.module.payload;

public class ModuleIdPayload {
	
	private String moduleId;
	
	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public String toString() {
		return "ModuleIdPayload [moduleId=" + moduleId + "]";
	}

}
