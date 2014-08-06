package com.hf.cloud.message.security;

import java.util.HashMap;

public class CaptchaEmailRequest extends CaptchaTextRequest {

	private String email;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	public CaptchaEmailRequest(String email) {
		super();
		this.email = email;
		
		HashMap<String, String> payload = new HashMap<String, String>();
		payload.put("email", email);
		setPayload(payload);
	}
}
