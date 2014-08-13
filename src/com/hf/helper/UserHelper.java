package com.hf.helper;

import android.content.Context;

import com.hf.ManagerFactory;
import com.hf.dao.impl.UserInfoDao;
import com.hf.info.UserInfo;
import com.hf.itf.IHFModuleManager;
import com.hf.util.HFModuleException;

public final class UserHelper {

	public static UserInfo getCurrentUser(Context context) {
		return new UserInfoDao(context).getActiveUserInfo();
	}
	
	public static boolean isUserLogined(Context context) {
		return getCurrentUser(context) != null;
	}
	
	public static boolean isUserTokenValid(Context context) {
		UserInfo userInfo = getCurrentUser(context);
		return userInfo != null && userInfo.isTokenValid();
	}
	
	public static String getCurrentUserToken(Context context) {
		UserInfo userInfo = getCurrentUser(context);
		if (userInfo == null) {
			return null;
		}else {
			return userInfo.getToken();	
		}
	}
	
	/**
	 * If the user token is expired, login again silently.
	 * @param context
	 * @return true if the current token is not expired or login success
	 */
	public static boolean doLoginIfNeed(Context context) {
		
		UserInfoDao userInfoDao = new UserInfoDao(context);
		UserInfo currentUserInfo = userInfoDao.getActiveUserInfo();
		if (currentUserInfo != null && !currentUserInfo.isTokenValid()) {
			try {
				ManagerFactory.getManager(context, IHFModuleManager.class).login(currentUserInfo.getUserName(), currentUserInfo.getPassword());
			} catch (HFModuleException e) {
				e.printStackTrace();
				if (e.getErrorCode() <= -101 && e.getErrorCode() >= -103) {
					currentUserInfo.setActive(false);
					currentUserInfo.setPassword(null);
					userInfoDao.saveUserInfo(currentUserInfo);
					return false;
				}
			}
		}
		
		return true;
	}
}
