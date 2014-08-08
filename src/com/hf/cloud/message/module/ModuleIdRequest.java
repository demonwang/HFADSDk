package com.hf.cloud.message.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Request;
import com.hf.cloud.message.module.payload.ModuleIdPayload;

class ModuleIdRequest extends Request {

	private ModuleIdPayload payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public ModuleIdPayload getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(ModuleIdPayload payload) {
		this.payload = payload;
	}

	public ModuleIdRequest() {
		this(null);
	}

	public ModuleIdRequest(ModuleIdPayload payload) {
		super();
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "ModuleIdRequest [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
