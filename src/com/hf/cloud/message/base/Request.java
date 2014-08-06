package com.hf.cloud.message.base;

import com.alibaba.fastjson.annotation.JSONField;

public class Request extends Message {

	private int responseTimeout;

	/**
	 * @return the responseTimeout
	 */
	@JSONField(name="RTO")
	public int getResponseTimeout() {
		return responseTimeout;
	}

	/**
	 * @param responseTimeout the responseTimeout to set
	 */
	@JSONField(name="RTO")
	public void setResponseTimeout(int responseTimeout) {
		this.responseTimeout = responseTimeout;
	}
	
	public Request() {
		super();
		setClassId(1);
		setName("Request");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Request [responseTimeout=" + responseTimeout + ", toString()="
				+ super.toString() + "]";
	}
}
