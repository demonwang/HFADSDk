package com.hf.cloud.message.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Response;
import com.hf.cloud.message.security.payload.UserPayload;

public class UserResponse extends Response{

	private UserPayload payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public UserPayload getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(UserPayload payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserResponse [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
