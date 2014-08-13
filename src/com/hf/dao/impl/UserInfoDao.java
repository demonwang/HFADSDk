package com.hf.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.hf.dao.IUserInfoDao;
import com.hf.info.UserInfo;
import com.j256.ormlite.dao.Dao;

public class UserInfoDao implements IUserInfoDao {
	
	private Dao<UserInfo, Integer> sqliteUserInfoDao;
	
	public UserInfoDao(Context context) {
		super();
		try {
			sqliteUserInfoDao = SqliteDBHelper.newInstance(context).getUserInfoDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserInfo getActiveUserInfo() {
		
		try {
			List<UserInfo> userInfos = sqliteUserInfoDao.queryForEq(UserInfo.FIELD_ACTIVE, true);
			if (userInfos != null && !userInfos.isEmpty()) {
				return userInfos.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public UserInfo getUserInfo(String userId) {
		return getUserInfoByField(UserInfo.FIELD_USER_ID, userId);
	}

	@Override
	public UserInfo getUserInfoByToken(String token) {
		return getUserInfoByField(UserInfo.FIELD_TOKEN, token);
	}

	@Override
	public boolean saveUserInfo(UserInfo userInfo) {
		
		if (userInfo == null) {
			throw new IllegalArgumentException("Parameter userInfo is null");
		}
		
		try {
			
			if (userInfo.get_id() > 0) {
				sqliteUserInfoDao.update(userInfo);
				return true;
			}
			
			if (!TextUtils.isEmpty(userInfo.getUserId())) {
				UserInfo _userInfo = getUserInfo(userInfo.getUserId());
				if (_userInfo != null) {
					userInfo.set_id(_userInfo.get_id());
					sqliteUserInfoDao.update(userInfo);
					return true;
				}
			}
			
			if (!TextUtils.isEmpty(userInfo.getToken())) {
				UserInfo _userInfo = getUserInfoByToken(userInfo.getToken());
				if (_userInfo != null) {
					userInfo.set_id(_userInfo.get_id());
					sqliteUserInfoDao.update(userInfo);
					return true;
				}
			}
			
			sqliteUserInfoDao.create(userInfo);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteUserInfo(String userId) {
		
		if (TextUtils.isEmpty(userId)) {
			throw new IllegalArgumentException("Parameter userId is null or empty");
		}
		
		try {
			List<UserInfo> userInfos = sqliteUserInfoDao.queryForEq(UserInfo.FIELD_USER_ID, userId);
			if (userInfos != null && !userInfos.isEmpty()) {
				sqliteUserInfoDao.delete(userInfos);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private UserInfo getUserInfoByField(String field, String value) {
		
		if (TextUtils.isEmpty(value)) {
			throw new IllegalArgumentException("Parameter " + field + " is null or empty");
		}
		
		try {
			List<UserInfo> userInfos = sqliteUserInfoDao.queryForEq(field, value);
			if (userInfos != null && !userInfos.isEmpty()) {
				return userInfos.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
