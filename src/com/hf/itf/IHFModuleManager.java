package com.hf.itf;

import java.net.SocketException;
import java.util.ArrayList;

import com.hf.info.KeyValueInfo;
import com.hf.info.ModuleInfo;
import com.hf.info.UserInfo;
import com.hf.util.HFModuleException;

public interface IHFModuleManager {
	/**
	 * 1.init net work;
	 * 2.init Local saver
	 * @throws SocketException 
	 */
	public void login() throws HFModuleException;
	public void logout() throws HFModuleException;
	public void registerUser();
	public UserInfo getUser() throws HFModuleException;
	public void setUser(UserInfo ui) throws HFModuleException;
	public void deleteUser();
	public void changePassword(String old,String newpswd) throws HFModuleException;
	public void retrievePassword(String receiverAddress, int receiverType) throws HFModuleException;
	public void setKeyValueInfo(KeyValueInfo kv) throws HFModuleException;
	public KeyValueInfo getKeyvalueInfo(String key) throws HFModuleException;
	public void deleteKeyValueInfo(String key) throws HFModuleException;
	public void registerEventListener(IHFModuleEventListener li);
	public void unregisterEventListener(IHFModuleEventListener li);
	public void removeAllListener();
	public ModuleInfo setModule(ModuleInfo mi) throws HFModuleException;
	public ModuleInfo getModule(String mac)throws HFModuleException;
	public void deleteModule(String mac) throws HFModuleException;
	public ArrayList<ModuleInfo> getAllModule() throws HFModuleException;
	public IHFModuleHelper getHFModuleHelper();
	
	public void startRemoteRefreshTimer();
	
	public void startLocalTimer() throws HFModuleException;
	public void stopLocalTimer();
}
