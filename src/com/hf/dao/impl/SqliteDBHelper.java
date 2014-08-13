package com.hf.dao.impl;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hf.info.UserInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

final class SqliteDBHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String TAG = "SqliteDBHelper";
	
	private static final String DB_NAME = "hiflying_sdk_internal.db";
	private static final int DB_VERSION_INIT = 1;
	private static final int DB_VERSION = DB_VERSION_INIT + 0;
	
	private Dao<UserInfo, Integer> userInfoDao;
	
	private static Context context;

	private SqliteDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	public static SqliteDBHelper newInstance(Context context) {
		SqliteDBHelper.context = context;
		return SqliteDBHelperInner.instance;
	}
	
	private static final class SqliteDBHelperInner{
		private static final SqliteDBHelper instance = new SqliteDBHelper(SqliteDBHelper.context.getApplicationContext());
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource cs) {
		
		Log.i(TAG, "onCreate");
		
		createTable(cs, UserInfo.class);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource cs, int arg2,
			int arg3) {
		Log.i(TAG, "onUpgrade");
		
		dropTable(cs, UserInfo.class);
	}

	public Dao<UserInfo, Integer> getUserInfoDao() throws SQLException {
		if (userInfoDao == null) {
			userInfoDao = getDao(UserInfo.class);
		}
		return userInfoDao;
	}
	
	private void createTable(ConnectionSource cs, Class<?> clazz) {
		
		try {
			TableUtils.createTable(cs, clazz);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(TAG, "Create database failed");
			
			//try again
			dropTable(cs, clazz);
			
			try {
				TableUtils.createTable(cs, clazz);
			} catch (Exception e2) {
			}
		}
	}

	private void dropTable(ConnectionSource cs, Class<?> clazz) {
		
		try {
			TableUtils.dropTable(cs, clazz, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
