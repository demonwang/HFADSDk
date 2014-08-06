package com.hf.cloud.message.security.payload;

public class UserIdPayload {
	
	private String userId;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserIdPayload [userId=" + userId + "]";
	}
}
