package com.hf.cloud.message.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Response;
import com.hf.info.captcha.CaptchaImageInfo;

public class CaptchaResponse extends Response {

	private CaptchaImageInfo payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public CaptchaImageInfo getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(CaptchaImageInfo payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CaptchaResponse [payload=" + payload + ", toString()="
				+ super.toString() + "]";
	}
}
