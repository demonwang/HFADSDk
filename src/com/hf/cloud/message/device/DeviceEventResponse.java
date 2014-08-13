package com.hf.cloud.message.device;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Response;

public class DeviceEventResponse extends Response {

	private Map<String, Map<String, String>> payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public Map<String, Map<String, String>> getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(Map<String, Map<String, String>> payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeviceEventResponse [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
