package com.hf.itf;

import java.util.ArrayList;

import com.hf.info.ModuleInfo;

public interface IHFModuleHelper {
	
	/**
	 * remote
	 */
	public ArrayList<ModuleInfo> getAllRemoteModuleInfo();
	/**
	 * 
	 * @param mis
	 */
	
	public void setAllRemoteModuleInfo(ArrayList<ModuleInfo> mis);
	/**
	 * 
	 * @param mi
	 */
	public void addRemoteModuleInfo(ModuleInfo mi);
	/**
	 * 
	 * @param mac
	 */
	public void removeRemoteModuleInfoByMac(String mac);
	/**
	 * 
	 */
	public void removeAllRemoteModuleInfo();
	/**
	 * 
	 * @param mac
	 * @return
	 */
	public ModuleInfo getRemoteModuleInfoByMac(String mac);
	/**
	 * 
	 * @param index
	 * @return
	 */
	public ModuleInfo getRemoteModuleInfoByIndex(int index);
	/**
	 * 
	 * @return
	 */
	public int getRemoteModuleInfoNum();
	/**
	 * 
	 * @return
	 */
	public int getRemoteModuleOnlineNum();
	/**
	 * 
	 */
	public void updatRemoteModuleLocalIp();
	
	/**
	 * local
	 */
	public ArrayList<ModuleInfo> getMyLocalModuleInfoAll();
	/**
	 * 
	 * @param mis
	 */
	public void setMyLocalModuleInfoAll(ArrayList<ModuleInfo> mis);
	/**
	 * 
	 * @param mi
	 */
	public void addMyLocalModuleInfo(ModuleInfo mi);
	/**
	 * 
	 * @param mac
	 */
	public void removeMyLocalModuleInfoByMac(String mac);
	/**
	 * 
	 */
	public void removeMyLocalModuleInfoAll();
	/**
	 * 
	 * @param mac
	 * @return
	 */
	public ModuleInfo getMyLocalModuleInfoByMac(String mac);
	/**
	 * 
	 * @param index
	 * @return
	 */
	public ModuleInfo getMyLocalModuleInfoByIndex(int index);
	/**
	 * 
	 * @return
	 */
	public int getMyLocalModuleInfoNum();
	/**
	 * 
	 * @return
	 */
	public int getMyLocalModuleOnlineNum();
	/**
	 * 
	 */
	public void updatLocalMyModuleLocalIp();
	/**
	 * shared
	 */
	
	public ArrayList<ModuleInfo> getAllLocalModuleInfo();
	/**
	 * 
	 * @param mis
	 */
	public void setAllLocalModuleInfo(ArrayList<ModuleInfo> mis);
	/**
	 * 
	 * @param mi
	 */
	public void addLocalModuleInfo(ModuleInfo mi);
	/**
	 * 
	 * @param mac
	 */
	public void removeLocalModuleInfoByMac(String mac);
	/**
	 * 
	 */
	public void removeAllLocalModuleInfo();
	/**
	 * 
	 * @param mac
	 * @return
	 */
	public ModuleInfo getLocalModuleInfoByMac(String mac);
	/**
	 * 
	 * @param index
	 * @return
	 */
	public ModuleInfo getLocalModuleInfoByIndex(int index);
	/**
	 * 
	 * @return
	 */
	public int getLocalModuleInfoNum();
	/**
	 * 
	 * @return
	 */
	public int getLocalModuleOnlineNum();
	/**
	 * 
	 */
	public void updatLocalModuleLocalIp();
	
	/**
	 * new
	 */
	
	public ArrayList<ModuleInfo> getAllNewModuleInfo();
	/**
	 * 
	 * @param mis
	 */
	public void setAllNewModuleInfo(ArrayList<ModuleInfo> mis);
	/**
	 * 
	 * @param mi
	 */
	public void addNewModuleInfo(ModuleInfo mi);
	/**
	 * 
	 * @param mac
	 */
	public void removeNewModuleInfoByMac(String mac);
	/**
	 * 
	 */
	public void removeAllNewModuleInfo();
	/**
	 * 
	 * @param mac
	 * @return
	 */
	public ModuleInfo getNewModuleInfoByMac(String mac);
	/**
	 * 
	 * @param index
	 * @return
	 */
	public ModuleInfo getNewModuleInfoByIndex(int index);
	/**
	 * 
	 * @return
	 */
	public int getNewModuleInfoNum();
	/**
	 * 
	 * @return
	 */
	public int getNewModuleOnlineNum();
	/**
	 * 
	 */
	public void updatNewModuleLocalIp();
}
