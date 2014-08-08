package com.hf.cloud.message.module;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Response;
import com.hf.info.ModuleInfo;

public class ModuleGetAllResponse extends Response {

	private List<ModuleInfo> payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public List<ModuleInfo> getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(List<ModuleInfo> payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "ModuleGetAllResponse [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
