package com.hf.helper;

import java.util.Date;

import com.hf.data.HFConfigration;
import com.hf.itf.IHFMainUserDataHelper;
import com.hf.itf.IHFRouterInfoHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HFLocalSaveHelper  {
	public static final String APP_LOCAL_DATA_SP = "APP_LOCAL_DATA_SP";
	private final String FIRSTRUN = "FIRSTRUN";
	private final String REGISTED = "REGISTED";
	private final String LASTRUNTIME = "LASTRUNTIME";
	private final String LASTRUNSTATE = "LASTRUNSTATE";
	private final String SERVDOMAIN = "SERVDOMAIN";
	private final String LOCALPORT = "LOCALPORT";
	public static final String MAINUSER = "MAINUSER";
	public static final String SHAREDUSER = "SHAREDUSER";
	public static final String ROUTERINFO = "ROUTERINFO";
	
	public static final String RUNSTAT_LOCAL = "LOCAL";
	public static final String RUNSTAT_REMOTE = "REMOTE"; 
	
	private SharedPreferences sp ;
	private static HFLocalSaveHelper me = null;
	
	private IHFRouterInfoHelper routerInfoHelper = null;
	private IHFMainUserDataHelper mainUserInfoHelper = null;
	
	
	private boolean isfristRun = true;
	private boolean isregisted = false;
	private String lastRunTime = "20140601";
	private String lastRunState = RUNSTAT_LOCAL;
	private String serverDomain = "www.iotworkshop.com";
	private int localPort = 38899;
	
	public static HFLocalSaveHelper getInstence(){
		if(me == null){
			me = new HFLocalSaveHelper();
		}
		return me;
	}
	
	public HFLocalSaveHelper init(){
		sp = HFConfigration.appContex.getSharedPreferences(APP_LOCAL_DATA_SP, Context.MODE_PRIVATE);
		this.isfristRun = sp.getBoolean(FIRSTRUN, true);
		this.isregisted = sp.getBoolean(REGISTED, false);
		this.lastRunState = sp.getString(LASTRUNTIME, "20140601");
		
		Editor e = sp.edit();
		e.putString(LASTRUNTIME, new Date().toGMTString());
		e.commit();
		
		this.lastRunState = sp.getString(LASTRUNSTATE, RUNSTAT_LOCAL);
		this.serverDomain = sp.getString(SERVDOMAIN, "http://115.29.164.59/usvc/");//"www.iotworkshop.com"
		this.localPort = sp.getInt(LOCALPORT, 38899);
		this.routerInfoHelper = new HFRouterInfoHelper();
		this.mainUserInfoHelper = new HFMainUserDataHelper();
		return this;
	}
	
	public IHFRouterInfoHelper getrouterInfoHelper(){
		if(routerInfoHelper == null){
			routerInfoHelper = new HFRouterInfoHelper();
		}
		return routerInfoHelper;
	}
	
	public IHFMainUserDataHelper getMainUserInfoHelper(){
		if(mainUserInfoHelper == null){
			mainUserInfoHelper = new HFMainUserDataHelper();
		}
		return mainUserInfoHelper;
	}

	public boolean isIsfristRun() {
		return isfristRun;
	}

	public void setIsfristRun(boolean isfristRun) {
		this.isfristRun = isfristRun;
		Editor e = sp.edit();
		e.putBoolean(FIRSTRUN, isfristRun);
		e.commit();
	}

	public boolean isIsregisted() {
		return isregisted;
	}

	public void setIsregisted(boolean isregisted) {
		this.isregisted = isregisted;
		Editor e = sp.edit();
		e.putBoolean(REGISTED, isregisted);
		e.commit();
	}

	public String getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(String lastRunTime) {
		this.lastRunTime = lastRunTime;
		Editor e = sp.edit();
		e.putString(LASTRUNTIME, lastRunTime);
		e.commit();
	}

	public String getLastRunState() {
		return lastRunState;
	}

	public void setLastRunState(String lastRunState) {
		this.lastRunState = lastRunState;
		Editor e = sp.edit();
		e.putString(LASTRUNSTATE, lastRunState);
		e.commit();
	}

	public String getServerDomain() {
		return serverDomain;
	}

	public void setServerDomain(String serverDomain) {
		this.serverDomain = serverDomain;
		Editor e = sp.edit();
		e.putString(SERVDOMAIN	, serverDomain);
		e.commit();
	}

	public int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
		Editor e = sp.edit();
		e.putInt(LOCALPORT, localPort);
		e.commit();
	}
}
