package com.hf.cloud.message.base;

import com.alibaba.fastjson.annotation.JSONField;

public class Response extends Message {
	
	public static final int RETURN_CODE_OK = 1;
	public static final int RETURN_CODE_FAILED = -1;
	
	private int returnCode;
	private String reason;
	
	/**
	 * @return the returnCode
	 */
	@JSONField(name="RC")
	public int getReturnCode() {
		return returnCode;
	}
	/**
	 * @param returnCode the returnCode to set
	 */
	@JSONField(name="RC")
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	/**
	 * @return the reason
	 */
	@JSONField(name="RN")
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	@JSONField(name="RN")
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Response() {
		super();
		setClassId(2);
		setName("Response");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [returnCode=" + returnCode + ", reason=" + reason
				+ ", toString()=" + super.toString() + "]";
	}
}
