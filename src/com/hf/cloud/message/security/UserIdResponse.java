package com.hf.cloud.message.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Response;
import com.hf.cloud.message.security.payload.UserIdPayload;

public class UserIdResponse extends Response {

	private UserIdPayload payload;

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public UserIdPayload getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(UserIdPayload payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserIdResponse [payload=" + payload + ", toString()="
				+ super.toString() + "]";
	}
}
