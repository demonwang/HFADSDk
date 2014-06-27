package com.hf.itf;

import com.hf.info.ModuleInfo;
/**
 * Event notification interface 
 * @author Administrator
 *
 */
public interface IHFModuleEventListener {
	/**
	 * @param mac
	 * @param t2data
	 */
	public void onEvent(String mac, byte[] t2data);
	
	/**
	 * 
	 */
	public void onCloudLogin(boolean loginstat);
	/**
	 * 
	 */
	public void onCloudLogout();
	
	/*
	 * 
	 * */
	public void onNewDevFind(ModuleInfo mi);
	/**
	 * 
	 */
	public void onLocalDevFind(ModuleInfo mi);
	/**
	 * 
	 * @param mac
	 * @param GM
	 */
	public void onGPIOEvent(String mac );
	/**
	 * 
	 */
	public void onTimerEvent(String mac,byte[] t2data);
	/*
	 * 
	 * */
	//public void onModuleConnenctOK(String mac);
	
	public void onUARTEvent(String mac,byte[] userData,boolean chanle);
}
