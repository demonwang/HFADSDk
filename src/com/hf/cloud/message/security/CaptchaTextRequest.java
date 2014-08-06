package com.hf.cloud.message.security;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Request;

public class CaptchaTextRequest extends Request {
	
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

	protected CaptchaTextRequest() {
		super();
		setClassId(10031);
	}
}
