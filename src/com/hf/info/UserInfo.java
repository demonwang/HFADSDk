package com.hf.info;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="UserInfo")
public class UserInfo implements java.io.Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String FIELD_USER_ID = "userId";
	public static final String FIELD_TOKEN = "token";
	public static final String FIELD_ACTIVE = "active";

	@DatabaseField(generatedId=true)
	private long _id;
	@DatabaseField
    private String userId;
	@DatabaseField
    private String accessKey;
	@DatabaseField
    private String displayName;
	@DatabaseField
    private String userName;
	@DatabaseField
    private String password;
	@DatabaseField
    private String sharePassword;
	@DatabaseField
    private String sharePasswordAgingTime;
	@DatabaseField
    private String cellPhone;
	@DatabaseField
    private String email;
	@DatabaseField
    private String idNumber;
	@DatabaseField
    private String createTime;
	@DatabaseField
    private String token;
	@DatabaseField
    private long tokenExpiredTime;
	@DatabaseField
    private boolean active;
	
	/**
	 * @return the _id
	 */
	public long get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(long _id) {
		this._id = _id;
	}

	/**
	 * @return the userId
	 */
	@JSONField(name="id")
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	@JSONField(name="id")
	public void setUserId(String userId) {
		this.userId = userId;
	}

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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	 * @return the sharePassword
	 */
	public String getSharePassword() {
		return sharePassword;
	}

	/**
	 * @param sharePassword the sharePassword to set
	 */
	public void setSharePassword(String sharePassword) {
		this.sharePassword = sharePassword;
	}

	/**
	 * @return the sharePasswordAgingTime
	 */
	public String getSharePasswordAgingTime() {
		return sharePasswordAgingTime;
	}

	/**
	 * @param sharePasswordAgingTime the sharePasswordAgingTime to set
	 */
	public void setSharePasswordAgingTime(String sharePasswordAgingTime) {
		this.sharePasswordAgingTime = sharePasswordAgingTime;
	}

	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tokenExpiredTime
	 */
	public long getTokenExpiredTime() {
		return tokenExpiredTime;
	}

	/**
	 * @param tokenExpiredTime the tokenExpiredTime to set
	 */
	public void setTokenExpiredTime(long tokenExpiredTime) {
		this.tokenExpiredTime = tokenExpiredTime;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @return true if only the user is active and has a token and also the token is not expired, otherwise false
	 */
	public boolean isTokenValid() {
		return active && !TextUtils.isEmpty(token) && System.currentTimeMillis() < tokenExpiredTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserInfo [_id=" + _id + ", userId=" + userId + ", accessKey="
				+ accessKey + ", displayName=" + displayName + ", userName="
				+ userName + ", password=" + password + ", sharePassword="
				+ sharePassword + ", sharePasswordAgingTime="
				+ sharePasswordAgingTime + ", cellPhone=" + cellPhone
				+ ", email=" + email + ", idNumber=" + idNumber
				+ ", createTime=" + createTime + ", token=" + token
				+ ", tokenExpiredTime=" + tokenExpiredTime + ", active="
				+ active + "]";
	}

	@Override
	protected UserInfo clone() throws CloneNotSupportedException {
		return (UserInfo)super.clone();
	}
}
