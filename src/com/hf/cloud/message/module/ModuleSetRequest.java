package com.hf.cloud.message.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Request;
import com.hf.info.ModuleInfo;

public class ModuleSetRequest extends Request {

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

	public ModuleSetRequest() {
		this(null);
	}
	
	public ModuleSetRequest(ModuleInfo payload) {
		super();
		this.payload = payload;
		setClassId(30011);
	}

	@Override
	public String toString() {
		return "ModuleSetRequest [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
