package com.hf.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hf.data.HFConfigration;
import com.hf.itf.IHFKeyValueHelper;
import com.hf.itf.IHFLocalModuleInfoHelper;
import com.hf.itf.IHFMainUserDataHelper;
import com.hf.itf.IHFServerModuleInfoHelper;

public class HFMainUserDataHelper implements IHFMainUserDataHelper{
	private final String USER_LOCAL_DATA_SP = "USER_LOCAL_DATA_SP";
	private final String USER = "USER";
	private final String SHOWNAME = "SHOWNAME";
	private final String PASSWORD = "PASSWORD";
	private final String PHONE = "PHONE";
	private final String EMAIL = "EMAIL";
	private final String DEFAULTMODULE = "DEFAULTMODULE";
	
	public static final String KEYVALUE = "KEYVALUE";
	public static final String SERVDATA = "SERVDATA";
	public static final String LOCALDATA = "LOCALDATA";
	
	private String userName;
	private String userNickName;
	private String pswd;
	private String phone;
	private String email;
	private String defModule;
	private IHFKeyValueHelper keyvalueHelper = null;
	private IHFLocalModuleInfoHelper localModuleInfoHelper= null;
	private IHFServerModuleInfoHelper serverModuleInfoHelper= null;
	
	private SharedPreferences sp ;
	public HFMainUserDataHelper(){
		sp = HFConfigration.appContex.getSharedPreferences(USER_LOCAL_DATA_SP, Context.MODE_PRIVATE);
		this.userName = sp.getString(USER, "");
		this.userNickName = sp.getString(SHOWNAME, "");
		this.pswd = sp.getString(PASSWORD, "");
		this.phone = sp.getString(PHONE, "");
		this.email = sp.getString(EMAIL, "");
		this.defModule = sp.getString(DEFAULTMODULE, "");
		this.keyvalueHelper = new HFKeyValueHelper();
		this.localModuleInfoHelper = new HFLocalModuleInfoHelper();
		this.serverModuleInfoHelper = new HFServerModuleInfoHelper();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
		Editor e = sp.edit();
		e.putString(USER, userName);
		e.commit();
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
		Editor e = sp.edit();
		e.putString(SHOWNAME, userNickName);
		e.commit();
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
		Editor e = sp.edit();
		e.putString(PASSWORD, pswd);
		e.commit();
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
		Editor e = sp.edit();
		e.putString(PHONE, phone);
		e.commit();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
		Editor e = sp.edit();
		e.putString(EMAIL, email);
		e.commit();
	}
	
	
	
	public String getDefModule() {
		return defModule;
	}
	public void setDefModule(String defModuleMac) {
		this.defModule = defModuleMac;
		Editor e = sp.edit();
		e.putString(DEFAULTMODULE, defModuleMac);
		e.commit();
	}
	public IHFKeyValueHelper getKeyvalueHelper() {
		if(keyvalueHelper == null){
			keyvalueHelper = new HFKeyValueHelper();
		}
		return keyvalueHelper;
	}

	public IHFLocalModuleInfoHelper getLocalModuleInfoHelper() {
		if(localModuleInfoHelper == null){
			localModuleInfoHelper = new HFLocalModuleInfoHelper();
		}
		return localModuleInfoHelper;
	}

	public IHFServerModuleInfoHelper getServerModuleInfoHelper() {
		if(serverModuleInfoHelper == null){
			serverModuleInfoHelper = new HFServerModuleInfoHelper();
		}
		return serverModuleInfoHelper;
	}
}
