package com.hf.cloud.message.device;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Request;

public class DeviceEventRequest extends Request {

	private Map<String, List<String>> payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public Map<String, List<String>> getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(Map<String, List<String>> payload) {
		this.payload = payload;
	}
	
	public DeviceEventRequest() {
		super();
		setClassId(20011);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeviceEventRequest [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
