package com.hf.cloud.message.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Response;
import com.hf.info.ModuleInfo;

public class ModuleInfoResponse extends Response {

	private ModuleInfo payload;
	
	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public ModuleInfo getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(ModuleInfo payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "ModuleSetResponse [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
