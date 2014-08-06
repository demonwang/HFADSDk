package com.hf.cloud.message.security.payload;

public class UserLoginPayload {
	private String accessKey;
	private String userName;
	private String password;
	private int agingTime;
	private String mac;
	private String clientName;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the agingTime
	 */
	public int getAgingTime() {
		return agingTime;
	}
	/**
	 * @param agingTime the agingTime to set
	 */
	public void setAgingTime(int agingTime) {
		this.agingTime = agingTime;
	}
	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserLoginPayload [accessKey=" + accessKey + ", userName="
				+ userName + ", password=" + password + ", agingTime="
				+ agingTime + ", mac=" + mac + ", clientName=" + clientName
				+ "]";
	}
}
