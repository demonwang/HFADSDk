package com.hf;

import com.hf.itf.IHFModuleManager;
import com.hf.itf.IHFSFManager;
import com.hf.manager.HFModuleManager;
import com.hf.manager.HISFManager;
/**
 * 
 * @author Administrator
 *
 */
public class ManagerFactory {
	private static IHFModuleManager manager = null;
	private static IHFSFManager sfmanger = null;
	
	
	public static IHFModuleManager getManager(){
		if(manager == null){
			manager = new HFModuleManager();
		}
		return manager;
	}
	
	public static IHFSFManager getFSFManager(){
		if(sfmanger == null){
			sfmanger = new HISFManager();
		}
		return sfmanger;
	}
	
}
