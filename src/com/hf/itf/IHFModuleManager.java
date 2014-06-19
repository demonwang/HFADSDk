package com.hf.itf;

import java.net.SocketException;
import java.util.ArrayList;

import android.content.Context;

import com.hf.info.KeyValueInfo;
import com.hf.info.ModuleInfo;
import com.hf.util.HFModuleException;

public interface IHFModuleManager {
	/**
	 * 1.init net work;
	 * 2.init Local saver
	 * @throws SocketException 
	 */
	public void initSystem(Context ctx) throws SocketException;
	public void login() throws HFModuleException;
	public void logout();
	public void registerUser();
	public void getUser();
	public void setUser();
	public void deleteUser();
	public void changePassword(String old,String newpswd);
	public void retrievePassword(String email);
	public void setKeyValueInfo(KeyValueInfo kv);
	public KeyValueInfo getKeyvalueInfo(String key);
	public void deleteKeyValueInfo(String key);
	public IHFModuleLocalManager getHFModuleLocalManager();
	public void registerEventListener(IHFModuleEventListener li);
	public void unregisterEventListener(IHFModuleEventListener li);
	public void removeAllListener();
	public void setModule(ModuleInfo mi);
	public void getModule(String mac);
	public void deleteModule(String mac);
	public ArrayList<ModuleInfo> getAllModule();
	public IHFModuleHelper getHFModuleHelper(IHFModuleManager manager);
	public IHFSFManager getFSFManager(IHFModuleManager manager);
	
	public void startRemoteRefreshTimer();
	
	public void startLocalTimer();
}
