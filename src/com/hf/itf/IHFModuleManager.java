package com.hf.itf;

import java.net.SocketException;
import java.util.ArrayList;

import com.hf.info.KeyValueInfo;
import com.hf.info.ModuleInfo;
import com.hf.info.UserInfo;
import com.hf.util.HFModuleException;

/**
 * many method of user manage
 * @author Administrator
 *
 */
public interface IHFModuleManager {
	/**
	 * Do userLogin before user this method please init HFconfigration
	 * @throws SocketException 
	 */
	public void login() throws HFModuleException;
	/**
	 * release User SID  set to unregistered mode
	 * @throws HFModuleException
	 */
	public void logout() throws HFModuleException;
	/**
	 * registerUser 
	 */
	public void registerUser()throws HFModuleException;
	/**
	 * get current userInfo
	 * @return
	 * @throws HFModuleException
	 */
	public UserInfo getUser() throws HFModuleException;
	/**
	 * modify userInfo to server sync HFconfigration & IHFmainUserDataHelper
	 * @param ui
	 * @throws HFModuleException
	 */
	public void setUser(UserInfo ui) throws HFModuleException;
	/**
	 * only for root user 
	 */
	public void deleteUser();
	/**
	 * change Password
	 * @param old
	 * @param newpswd
	 * @throws HFModuleException
	 */
	public void changePassword(String old,String newpswd) throws HFModuleException;
	/**
	 * find pswd
	 * @param receiverAddress
	 * @param receiverType
	 * @throws HFModuleException
	 */
	public void retrievePassword(String receiverAddress, int receiverType) throws HFModuleException;
	/**
	 * save develpoer private Data to server use Keyvalue
	 * @param kv
	 * @throws HFModuleException
	 */
	public void setKeyValueInfo(KeyValueInfo kv) throws HFModuleException;
	/**
	 * get develpoer private Data frome server
	 * @param key
	 * @return
	 * @throws HFModuleException
	 */
	public KeyValueInfo getKeyvalueInfo(String key) throws HFModuleException;
	/**
	 * delete develpoer private Data
	 * @param key
	 * @throws HFModuleException
	 */
	public void deleteKeyValueInfo(String key) throws HFModuleException;
	/**
	 * register IHFModuleEventListener for get system event ;
	 * @param li
	 */
	public void registerEventListener(IHFModuleEventListener li);
	/**
	 * 
	 * @param li
	 */
	public void unregisterEventListener(IHFModuleEventListener li);
	/**
	 * 
	 */
	public void removeAllListener();
	/**
	 * add or modify ModuleInfo   
	 *
	 * if id==null 
	 * 	add 
	 * else 
	 *	 modify
	 * @param mi
	 * @return
	 * @throws HFModuleException
	 */
	public ModuleInfo setModule(ModuleInfo mi) throws HFModuleException;
	/**
	 * get ModuleInfo
	 * @param mac
	 * @return
	 * @throws HFModuleException
	 */
	public ModuleInfo getModule(String mac)throws HFModuleException;
	/**
	 * delete ModuleInfo
	 * @param mac
	 * @throws HFModuleException
	 */
	public void deleteModule(String mac) throws HFModuleException;
	/**
	 * get all ModuleInfo
	 * @return
	 * @throws HFModuleException
	 */
	public ArrayList<ModuleInfo> getAllModule() throws HFModuleException;
	/**
	 * get ModuleHelper  for (get set add delete )localData of ModuleInfo 
	 * @return
	 */
	public IHFModuleHelper getHFModuleHelper();
	/**
	 * get manager for send smrtlink  // setModuleInfo of local
	 * @return
	 */
	public IHFModuleLocalManager getHFModuleLocalManager();
	/**
	 * Refresh module status loop 
	 */
	public void startRemoteRefreshTimer();
	/**
	 * Starts sending local beat command
	 * Here will perform login if registered but are not logged in
	 * @throws HFModuleException
	 */
	public void startLocalTimer() throws HFModuleException;
	/**
	 * stop sending local beat
	 */
	public void stopLocalTimer();
	/**
	 *Judge Users status
	 * @return
	 */
	public boolean isCloudChannelLive();
}
