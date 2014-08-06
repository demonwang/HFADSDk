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
	
	private ManagerFactory() {
		manager = new HFModuleManager();
		sfmanger = new HISFManager();
	}
	
	private static class ManagerFactoryInner {
		private static ManagerFactory managerFactory = new ManagerFactory();
	}
	
	public static ManagerFactory getInstance() {
		return ManagerFactoryInner.managerFactory;
	}
	
	public IHFModuleManager getModuleManager(){
		return manager;
	}
	
	public IHFSFManager getSFManager(){
		return sfmanger;
	}
}
