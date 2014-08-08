package com.hf.itf;

import java.util.ArrayList;
import java.util.List;

import com.hf.info.KeyValueInfo;
import com.hf.info.MessageReceiver;
import com.hf.info.ModuleInfo;
import com.hf.info.UserInfo;
import com.hf.info.captcha.CaptchaImageInfo;
import com.hf.util.HFModuleException;

/**
 * many method of user manage
 * @author Administrator
 *
 */
public interface IHFModuleManager {
	
	/**
	 * @return
	 * @throws HFModuleException
	 */
	public CaptchaImageInfo captchaText(MessageReceiver captchaReceiver) throws HFModuleException;
	/**
	 * @return
	 * @throws HFModuleException
	 */
	public CaptchaImageInfo captchaImage() throws HFModuleException;
	
	/**
	 * login with account and password
	 * @param username
	 * @param password
	 * @return the session id
	 * @throws HFModuleException
	 */
	public String login(String username, String password) throws HFModuleException;
	
	/**
	 * logout from the cloud service
	 * @param sessionId	the session id of the user to logout 
	 * @throws HFModuleException
	 */
	public void logout(String sessionId) throws HFModuleException;
	
	/**
	 * Register a user in cloud service 
	 * @param userInfo the user info to register
	 * @param captcha the captcha text
	 * @return the user id 
	 * @throws HFModuleException
	 */
	public String registerUser(UserInfo userInfo, String captcha) throws HFModuleException;
	
	/**
	 * get current userInfo
	 * @return
	 * @throws HFModuleException
	 */
	public UserInfo getUser(String sessionId) throws HFModuleException;
	
	/**
	 * update user info to cloud service
	 * @param sessionId the session id returned after user login 
	 * @param userInfo the user info to update
	 * @throws HFModuleException
	 */
	public void setUser(String sessionId, UserInfo userInfo) throws HFModuleException;
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
	 * retrieve password
	 * @param receiver the password will send to, currently support email or sms 
	 * @throws HFModuleException
	 */
	public void retrievePassword(MessageReceiver receiver) throws HFModuleException;
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
	public List<ModuleInfo> getAllModule() throws HFModuleException;
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
