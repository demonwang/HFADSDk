package com.hf.cloud.message.security;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.hf.cloud.message.base.Request;
import com.hf.cloud.message.security.payload.RetrievePasswordPayload;

public class RetrievePasswordRequest extends Request {

//	private Map<String, String> payload = new HashMap<String, String>();
	private RetrievePasswordPayload payload;
	

//	/**
//	 * @return the payload
//	 */
//	@JSONField(name="PL")
//	public Map<String, String> getPayload() {
//		return payload;
//	}
//
//	/**
//	 * @param payload the payload to set
//	 */
//	@JSONField(name="PL")
//	public void setPayload(Map<String, String> payload) {
//		this.payload = payload;
//	}

	/**
	 * @return the payload
	 */
	@JSONField(name="PL")
	public RetrievePasswordPayload getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@JSONField(name="PL")
	public void setPayload(RetrievePasswordPayload payload) {
		this.payload = payload;
	}

	public RetrievePasswordRequest() {
		this(null);
	}

	public RetrievePasswordRequest(RetrievePasswordPayload payload) {
		super();
		setClassId(10041);
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RetrievePasswordRequest [payload=" + payload + ", "
				+ super.toString() + "]";
	}
}
