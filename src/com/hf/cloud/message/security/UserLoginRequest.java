package com.hf.cloud.message.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Request;
import com.hf.cloud.message.security.payload.UserLoginPayload;

public class UserLoginRequest extends Request {

	private UserLoginPayload payload;
	
	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public UserLoginPayload getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(UserLoginPayload payload) {
		this.payload = payload;
	}

	public UserLoginRequest() {
		super();
		setClassId(10011);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserLoginRequest [payload=" + payload + ", super.toString()="
				+ super.toString() + "]";
	}
}
