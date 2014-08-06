package com.hf.cloud.message.security.payload;

public class RetrievePasswordPayload {

	private String accessKey;
	private String sms;
	private String email;
	/**
	 * @return the accessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}
	/**
	 * @param accessKey the accessKey to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	/**
	 * @return the sms
	 */
	public String getSms() {
		return sms;
	}
	/**
	 * @param sms the sms to set
	 */
	public void setSms(String sms) {
		this.sms = sms;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "RetrievePasswordPayload [accessKey=" + accessKey + ", sms="
				+ sms + ", email=" + email + "]";
	}
}
