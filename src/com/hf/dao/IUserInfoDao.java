package com.hf.dao;

import com.hf.info.UserInfo;

/**
 * It's provide to access user info stored in local storage
 * @author Zhang GuoYin
 * @see UserInfo
 */
public interface IUserInfoDao {

	/**
	 * Get the logined user info
	 * @return
	 */
	public UserInfo getActiveUserInfo();
	
	/**
	 * Get user info by userId
	 * @param userId the user id, not be null or empty
	 * @return
	 * @throws IllegalArgumentException if parameter userId is null or empty
	 */
	public UserInfo getUserInfo(String userId);
	
	/**
	 * Get user info by token
	 * @param token the user token, not be null or empty
	 * @return
	 * @throws IllegalArgumentException if parameter token is null or empty
	 */
	public UserInfo getUserInfoByToken(String token);
	
	/**
	 * Save user info, it will update a exist userInfo or add a new one
	 * @param userInfo the userInfo to save
	 * @return true if success, otherwise false
	 * @throws IllegalArgumentException if parameter userInfo is null
	 */
	public boolean saveUserInfo(UserInfo userInfo);

	/**
	 * Delete user info by userId
	 * @param userId the userId of UserInfo to delete
	 * @return true if success, otherwise false
	 * @throws IllegalArgumentException if parameter userId is null
	 */
	public boolean deleteUserInfo(String userId);
}
