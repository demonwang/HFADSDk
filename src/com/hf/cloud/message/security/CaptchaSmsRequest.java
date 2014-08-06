package com.hf.cloud.message.security;

import java.util.HashMap;

public class CaptchaSmsRequest extends CaptchaTextRequest {

	private String sms;

	/**
	 * @return the sms
	 */
	public String getSms() {
		return sms;
	}

	public CaptchaSmsRequest(String sms) {
		super();
		this.sms = sms;
		
		HashMap<String, String> payload = new HashMap<String, String>();
		payload.put("sms", sms);
		setPayload(payload);
	}
}
