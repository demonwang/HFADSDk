package com.hf.itf;

import java.util.ArrayList;

import com.hf.info.ModuleInfo;

public interface IHFModuleHelper {
	
	/**
	 * remote
	 */
	public ArrayList<ModuleInfo> getAllRemoteModuleInfo();
	
	public void setAllRemoteModuleInfo(ArrayList<ModuleInfo> mis);
	
	public void addRemoteModuleInfo(ModuleInfo mi);
	
	public void removeRemoteModuleInfoByMac(String mac);
	
	public void removeAllRemoteModuleInfo();
	
	public ModuleInfo getRemoteModuleInfoByMac(String mac);
	
	public ModuleInfo getRemoteModuleInfoByIndex(int index);
	
	public int getRemoteModuleInfoNum();
	
	public int getRemoteModuleOnlineNum();
	
	public void updatRemoteModuleLocalIp();
	
	/**
	 * local
	 */
	public ArrayList<ModuleInfo> getMyLocalModuleInfoAll();
	
	public void setMyLocalModuleInfoAll(ArrayList<ModuleInfo> mis);
	
	public void addMyLocalModuleInfo(ModuleInfo mi);
	
	public void removeMyLocalModuleInfoByMac(String mac);
	
	public void removeMyLocalModuleInfoAll();
	
	public ModuleInfo getMyLocalModuleInfoByMac(String mac);
	
	public ModuleInfo getMyLocalModuleInfoByIndex(int index);
	
	public int getMyLocalModuleInfoNum();
	
	public int getMyLocalModuleOnlineNum();
	
	public void updatLocalMyModuleLocalIp();
	/**
	 * shared
	 */
	
	public ArrayList<ModuleInfo> getAllLocalModuleInfo();
	
	public void setAllLocalModuleInfo(ArrayList<ModuleInfo> mis);
	
	public void addLocalModuleInfo(ModuleInfo mi);
	
	public void removeLocalModuleInfoByMac(String mac);
	
	public void removeAllLocalModuleInfo();
	
	public ModuleInfo getLocalModuleInfoByMac(String mac);
	
	public ModuleInfo getLocalModuleInfoByIndex(int index);
	
	public int getLocalModuleInfoNum();
	
	public int getLocalModuleOnlineNum();
	
	public void updatLocalModuleLocalIp();
	
	/**
	 * new
	 */
	
	public ArrayList<ModuleInfo> getAllNewModuleInfo();
	
	public void setAllNewModuleInfo(ArrayList<ModuleInfo> mis);
	
	public void addNewModuleInfo(ModuleInfo mi);
	
	public void removeNewModuleInfoByMac(String mac);
	
	public void removeAllNewModuleInfo();
	
	public ModuleInfo getNewModuleInfoByMac(String mac);
	
	public ModuleInfo getNewModuleInfoByIndex(int index);
	
	public int getNewModuleInfoNum();
	
	public int getNewModuleOnlineNum();
	
	public void updatNewModuleLocalIp();
	
	/*
	 * 
	 */
}
