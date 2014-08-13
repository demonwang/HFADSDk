package com.hf.cloud.message.device;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Response;

public class DeviceResponse extends Response {

	private Map<String, String> payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public Map<String, String> getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(Map<String, String> payload) {
		this.payload = payload;
	}
}
